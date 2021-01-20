from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product, Address, Order, Purchase, Card, ProductList, ProductListItem
from ..serializers.product_list_serializer import ProductListResponseSerializer
from ..utils import authentication, order_status


@api_view(['POST', 'GET'])
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
        product_lists = ProductList.objects.filter(user_id=user.pk)

        for list in product_lists:
            list_item = ProductListItem.objects.filter(product_list_id=list.pk)
            products_serializer = ProductListResponseSerializer(list_item, many=True)

            response_product_list.append({
                                'list_id':list.pk, 
                                'name':list.name,
                                'products': products_serializer.data 
                                })

        return Response({'status': {'successful': True, 'message':'Product Lists are uccessfully sent'},'lists':response_product_list})


@api_view(['DELETE'])
@permission_classes([permissions.AllowAnonymous])
def product_list_delete(request, list_id):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]

    product_list = ProductList.objects.filter(id=int(list_id))
    if len(product_list) == 0:
        return Response({'status': { 'successful': False, 'message': "This product list is invalid."}})
    else:
        product_list.delete()

    return Response({'status': { 'successful': True, 'message': "This product list is successfully deleted."}})


@api_view(['POST', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_product_list_item(request, list_id, product_id):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]
    
    if request.method == "POST":
        product_list = ProductList.objects.filter(id=int(list_id))
        if len(product_list) == 0:
            return Response({'status': { 'successful': False, 'message': "This product list is invalid."}})

        product_list_item = ProductListItem(product_list_id=product_list.first().pk, product_id=int(product_id))
        product_list_item.save()

        return Response({'status': { 'successful': True, 'message': "Product is successfully added to list."}})

