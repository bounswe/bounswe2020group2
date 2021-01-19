from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product, Address, Order, Purchase, Card, ProductList, ProductListItem
from ..serializers import product_serializer
from ..utils import authentication, order_status


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def product_list_create(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]
    
    if request.method == "POST":
        list_name = request.data.get("name")

        product_list = ProductList(user_id=user.pk, name=list_name)
        product_list.save()

        return Response({'id': product_list.pk, 'status': { 'successful': True, 'message': "List is successfully created."}})

    elif request.method == "GET":
        response_product_list = []
        product_lists = ProductList.objects.filter(user_id=user.pk, many=True)

        for list in product_lists:
            list_item = ProductListItem.objects.filter(product_list_id=list.pk, many=True)
            products_serializer = product_serializer.ProductResponseSerializer(list_item, many=True)

            product_lists.append({
                                'list_id':list.pk, 
                                'name':list.name,
                                'products': products_serializer.data 
                                })

        return Response({'status': {'successful': True, 'message':'Product Lists are uccessfully sent'},'lists':product_lists})
