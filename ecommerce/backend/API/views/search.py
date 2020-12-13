from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser
from rest_framework.renderers import JSONRenderer

from API.utils import permissions, Role
from API.models import Product, Subcategory
from django.db.models import Q
#from API.serializers import search_serializer
from API.serializers.product_serializer import ProductSerializer

@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def products(request):
#    print(request)
    query_data=JSONParser().parse(request)
#    product_search_serializer = search_serializer.SearchProductsSerializer(request)
    print(query_data)
    query_set = Product.objects.all()
    if "query" in query_data:
        pass #case insensitive search on title
    
        pass
    if "subcategory" in query_data:
        query_set = query_set.filter(subcategory_id=query_data["subcategory"])
    elif "category" in query_data:
        category_Q = Q()
        # Get all subcategories under the given category and create a Q object.
        for sub_id in Subcategory.objects.filter(category_id=query_data["category"]):
            category_Q = (category_Q | Q(subcategory_id=sub_id))
        query_set = query_set.filter(category_Q)
    if "brand" in query_data:
        if not query_data["brand"]:
            pass #brand list is empty, assume no filtering
        else:
            pass
            #brand_q=Q(brand=)
    if "max_price" in query_data:
        pass #filter
    #SORTING
    if "sort_by" not in query_data:
        pass #best-sellers
    elif query_data["sort_by"]=="newest-arrivals":
        pass #sort 
    #PAGINATION
    page = 0
    page_size = 10
    if "page" in query_data:
        page = query_data["page"];
    if "page_size" in query_data:
        page_size = query_data["page_size"];
    #LIMIT MIGHT PASS THE NUMBER OF ELEMENTS
    query_set=query_set[page*page_size:page_size]
#    user_serializer = search_serializer.SearchProductSerializer(request)
    print(query_set)
    serializer = ProductSerializer(query_set, many=True)
    print(serializer.data)
    return Response(serializer.data)






