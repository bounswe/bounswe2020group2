from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser

from API.utils import permissions, Role
from API.utils.jwttoken import generate_access_token
from API.utils.crypto import Crypto
from API.models import User, ShoppingCartItem, Product
from API.serializers import account_serializer, shopping_cart_serializer
from django.contrib.auth.decorators import login_required

@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def add_shopping_cart_item(request, id):
    data = JSONParser().parse(request)
    serializer = shopping_cart_serializer.ShoppingCartRequestSerializer(data=data)
    if not serializer.is_valid():
        return Response("Invalid input")

    # userId = serializer.validated_data.get("userId")
    # if userId != int(id):
    #     return Response("Invalid user")

    user = User.objects.filter(pk=int(id)).first()
    amount = serializer.validated_data.get("amount")
    productId = serializer.validated_data.get("productId")
    product = Product.objects.filter(pk=productId).first()
    stock_amount = product.stock_amount
    name = product.name
    shopping_cart = ShoppingCartItem.objects.filter(customer_id=user.pk).filter(product_id=productId).first()

    if shopping_cart is None:
        shopping_cart = ShoppingCartItem(customer=user, product=product, amount=amount, name=name)
    else:
        shopping_cart.amount+=amount
        
    if shopping_cart.amount > stock_amount:
            return Response("Stock Amount is reached")
    shopping_cart.save()

    return Response("Success")

# @login_required(login_url="login/")