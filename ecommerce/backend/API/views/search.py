from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser
from rest_framework.renderers import JSONRenderer

from API.utils import permissions, Role
from API.models import Product, ProductIndex, Subcategory, Vendor, Brand
from django.db.models import Q
#from API.serializers import search_serializer
from API.serializers.product_serializer import ProductResponseSerializer
from API.serializers.vendor_serializer import VendorResponseSerializer

import string
import urllib.request
import ssl
import json
import requests

API_URL = "https://api.datamuse.com/words?ml="

# these are the stopwords of the NLTK library, taken from https://gist.github.com/sebleier/554280
with open('stopwords.txt', 'r', encoding='latin-1') as f:   
    stopwords = [line.strip() for line in f.readlines()]

# preprocess the sentence by applyin case-folding, punctutation removal and tokenization
def preprocess(sentence):
    global stopwords
    sentence = sentence.casefold() # case-folding
    sentence = sentence.translate(str.maketrans(string.punctuation, ' '*len(string.punctuation))) #punctuation removal
    sentence = sentence.split() #tokenization
    sentence = [word for word in sentence if not word in stopwords] # stopword removal
    return sentence

# gets similar queries from the Datamuse API
def get_similar_queries(query):
    processed_query = '+'.join(preprocess(query))
    # get the content of the API
    r = requests.get(API_URL+processed_query)
    results = r.content
    results = json.loads(results)
    # return words of the top 10 results
    similar_queries = [d['word'] for d in results[:10]]
    return similar_queries

# conduct semantic search for the given query
def semantic_search_for(query):
    similar_queries = get_similar_queries(query)
    indexed_products = ProductIndex.objects.all()
    or_filter = Q()
    for query in similar_queries:
        or_filter |= Q(text__icontains=query)
    indexed_products = indexed_products.filter(or_filter).all().distinct()
    return indexed_products

@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def products(request):
    semantic_search_for('telephone')
#    print(request)
    query_data=JSONParser().parse(request)
#    product_search_serializer = search_serializer.SearchProductsSerializer(request)
#    print(query_data)
    query_set = Product.objects.all()
    if "query" in query_data:
        query_set = semantic_search_for(query_data["query"])
    if "subcategory" in query_data:
        query_set = query_set.filter(subcategory_id=query_data["subcategory"])
    elif "category" in query_data:
        category_q = Q()
        # Get all subcategories under the given category and create a Q object.
        for sub_id in Subcategory.objects.filter(category_id=query_data["category"]):
            category_q = (category_q | Q(subcategory_id=sub_id))
        query_set = query_set.filter(category_q)
    if "vendor" in query_data:
        query_set = query_set.filter(vendor_id=query_data["vendor"])
    if "brand" in query_data:
        if not query_data["brand"]:
            pass #brand list is empty, assume no filtering
        else:
            brand_q = Q()
            # Create a Q object for all brands.
            for brand_id in query_data["brand"]:
                brand_q = (brand_q | Q(brand_id=brand_id))
            query_set = query_set.filter(brand_q)
    if "max_price" in query_data:
        query_set = query_set.filter(price__lte=query_data["max_price"])
    if "min_rating" in query_data:
        query_set = query_set.filter(rating__gte=query_data["min_rating"])
    #SORTING
    s_order = ""
    if "sort_order" in query_data:
        if query_data["sort_order"]=="decreasing":
            s_order = "-"
    if "sort_by" not in query_data:
        query_set = query_set.order_by("-rating")
    elif query_data["sort_by"]=="arrival":
        query_set = query_set.order_by(s_order+"creation_date") #sort
    elif query_data["sort_by"]=="price":
        query_set = query_set.order_by(s_order+"price")
    elif query_data["sort_by"]=="rating":
        query_set = query_set.order_by(s_order+"rating")
    # TODO Comment Count and Best Sellers ordering
    page = 0
    page_size = 10
    if "page" in query_data:
        page = query_data["page"];
    if "page_size" in query_data:
        page_size = query_data["page_size"];
    #LIMIT MIGHT PASS THE NUMBER OF ELEMENTS
    total_items=query_set.count()
    query_set=query_set[page*page_size:(page+1)*(page_size)]
#    user_serializer = search_serializer.SearchProductSerializer(request)
#    print(query_set)
    serializer = ProductResponseSerializer(query_set, many=True)
#    print(serializer.data)
    response_data = {"pagination":{"page":page,"page_size":page_size,"total_items":total_items},"products":serializer.data}
    return Response( { "data":response_data} )
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def vendors(request):
#    print(request)
    query_data=JSONParser().parse(request)
#    product_search_serializer = search_serializer.SearchProductsSerializer(request)
#    print(query_data)
    query_set = Vendor.objects.all()
    if "query" in query_data:
        query_set = query_set.filter(Q(first_name__icontains=query_data["query"]) | Q(last_name__icontains=query_data["query"]))
    vendors = query_set
    if "min_rating" in query_data:
        tempvendors = []
        for vendor in vendors:
            if vendor.rating_count==0 or vendor.total_rating/vendor.rating_count>=query_data["min_rating"]:
                tempvendors.append(vendor)
        vendors = tempvendors
    if "subcategory" in query_data:
        tempvendors = []
        for vendor in vendors:
            if Product.objects.filter(subcategory_id=query_data["subcategory"],vendor_id=vendor.id).exists():
                tempvendors.append(vendor)
        vendors = tempvendors
    elif "category" in query_data:
        tempvendors = []
        category_q = Q()
        for sub_id in Subcategory.objects.filter(category_id=query_data["category"]):
            category_q = (category_q | Q(subcategory_id=sub_id))
        for vendor in vendors:
            if Product.objects.filter(category_q,vendor_id=vendor.id).exists():
                tempvendors.append(vendor)
        vendors = tempvendors
    if "brand" in query_data:
        if not query_data["brand"]:
            pass #brand list is empty, assume no filtering
        else:
            tempvendors = []
            brand_q = Q()
            # Create a Q object for all brands.
            for brand_id in query_data["brand"]:
                brand_q = (brand_q | Q(brand_id=brand_id))
            for vendor in vendors:
                if Product.objects.filter(brand_q,vendor_id=vendor.id).exists():
                    tempvendors.append(vendor)
            vendors = tempvendors
    page = 0
    page_size = 10
    if "page" in query_data:
        page = query_data["page"];
    if "page_size" in query_data:
        page_size = query_data["page_size"];
    #LIMIT MIGHT PASS THE NUMBER OF ELEMENTS
    total_items=len(vendors)
    vendors=vendors[page*page_size:(page+1)*(page_size)]
#    user_serializer = search_serializer.SearchProductSerializer(request)
#    print(query_set)
    serializer = VendorResponseSerializer(vendors, many=True)
#    print(serializer.data)
    response_data = {"pagination":{"page":page,"page_size":page_size,"total_items":total_items},"vendors":serializer.data}
    return Response( { "data":response_data} )
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def brands(request):
#    print(request)
    query_data=JSONParser().parse(request)
#    product_search_serializer = search_serializer.SearchProductsSerializer(request)
#    print(query_data)
    query_set = Brand.objects.all()
    if "query" in query_data:
        query_set = query_set.filter(name__icontains=query_data["query"])
    brands = query_set
    if "subcategory" in query_data:
        tempbrands = []
        for brand in brands:
            if Product.objects.filter(subcategory_id=query_data["subcategory"],brand_id=brand.id).exists():
                tempbrands.append(brand)
        brands = tempbrands
    elif "category" in query_data:
        tempbrands = []
        category_q = Q()
        for sub_id in Subcategory.objects.filter(category_id=query_data["category"]):
            category_q = (category_q | Q(subcategory_id=sub_id))
        for brand in brands:
            if Product.objects.filter(category_q,brand_id=brand.id).exists():
                tempbrands.append(brand)
        brands = tempbrands
    page = 0
    page_size = 10
    if "page" in query_data:
        page = query_data["page"];
    if "page_size" in query_data:
        page_size = query_data["page_size"];
    #LIMIT MIGHT PASS THE NUMBER OF ELEMENTS
    total_items=len(brands)
    brands=brands[page*page_size:(page+1)*(page_size)]
#    user_serializer = search_serializer.SearchProductSerializer(request)
#    print(query_set)
#    print(serializer.data)
    serializedBrands = list(map(lambda brand: {"id":brand.id,"name":brand.name}, brands))
    response_data = {"pagination":{"page":page,"page_size":page_size,"total_items":total_items},"brands":serializedBrands}
    return Response( { "data":response_data} )