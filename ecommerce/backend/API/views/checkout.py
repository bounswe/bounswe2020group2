from ecommerce.backend.API.models.purchase import Order
from API.models import product
from API.models import purchase
from API.models.purchase import Purchase
from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product, Address, Order
from ..serializers import checkout_product_serializer, checkout_shopping_cart_serializer, address_serializer, shopping_cart_serializer
from ..utils import authentication, order_status


@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def checkout_details(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)

    items = ShoppingCartItem.objects.filter(customer_id=user[0].pk)
    serializers = shopping_cart_serializer.ShoppingCartResponseSerializer(items, many=True)

    amount = 0
    price = 0 
    discount = 0
    total_discount = 0
    products_price = 0
    delivery_price = 7.9
    total_price = 0

    for serializer in serializers.data:
        amount = serializer.get("amount")
        price = serializer.get("product")["price"]
        discount = serializer.get("product")["discount"]
        total_discount += amount*price*discount
        products_price += amount*price
    
    total_price = '{:.2f}'.format(products_price + delivery_price - total_discount)
    products_price = '{:.2f}'.format(products_price)
    delivery_price = '{:.2f}'.format(delivery_price)
    total_discount = '{:.2f}'.format(total_discount)

    context = {
                "products_price": products_price,
                "delivery_price": delivery_price,
                "discount": total_discount,
                "total_price": total_price
                }

    return Response(context)


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def checkout_payment(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)

    address_id = request.data["address_id"]
    address = Address.objects.filter(id=address_id)
    address_s = address_serializer.AddressResponseSerializer(address)

    items = ShoppingCartItem.objects.filter(customer_id=user[0].pk)
    serializers = shopping_cart_serializer.ShoppingCartResponseSerializer(items, many=True)

    order = Order(user=user)

    amount = 0
    unit_price = 0 

    for serializer in serializers.data:
        amount = serializer.get("amount")
        product_id = serializer.get("product")["id"]
        unit_price = serializer.get("product")["price"]
        name = serializer.get("product")["amount"]
        vendor = serializer.get("product")["vendor"]
        status = order_status.OrderStatus.ACCEPTED
        purchase = Purchase(user=user, product_id=product_id, amount=amount, unit_price=unit_price, name=name, status=status,\
                             address_id=address_id, vendor=vendor, order=order)
        purchase.save()

    return Response({'status': { 'successful': True, 'message': "Payment process is successfully satisfied."}})


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def checkout_cancel_order(request, id):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)

    order = Order.objects.get(pk=int(id))
    purchase.status = order_status.OrderStatus.CANCELLED
    purchase.update()