from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser
from rest_framework.renderers import JSONRenderer

from API.utils import permissions, Role
from API.models import Product
from API.serializers import search_serializer

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
    if "category" in query_data:
        pass #filter
    if "subcategory" in query_data:
        pass #filter
    if "brand" in query_data:
        pass #if not empty add filter
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
    return Response()






