from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers import checkout_product_serializer, checkout_shooping_cart_serializer
from ..utils import authentication


@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def checkout_details(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)

    items = ShoppingCartItem.objects.filter(customer_id=user[0].pk).values('id', 'product_id', 'amount')
    serializers = checkout_shooping_cart_serializer.CheckoutShoppingCartSerializer(items, many=True)

    products_price = 0
    delivery_price = 7.9
    discount_percentage = 0.1
    discount = 0
    total_price = 0


    for serializer in serializers.data:
        amount = serializer.get("amount")
        price = serializer.get("product")["price"]
        products_price += amount*price

        print(amount, price)

    discount = products_price * discount_percentage
    total_price = products_price - discount + delivery_price

    context = {
                "products_price": products_price,
                "delivery_price": delivery_price,
                "discount": discount,
                "total_price": total_price
                }
    return Response(context)
