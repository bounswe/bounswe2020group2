from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product, Address, Order, Purchase, Card, ProductList, ProductListItem
from ..serializers import checkout_product_serializer, checkout_shopping_cart_serializer, address_serializer, shopping_cart_serializer
from ..utils import authentication, order_status


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def product_list_create(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]

    list_name = request.data.get("name")

    product_list = ProductList(user_id=user.pk, name=list_name)
    product_list.save()

    return Response({'id': product_list.pk, 'status': { 'successful': True, 'message': "List is successfully created."}})
    
