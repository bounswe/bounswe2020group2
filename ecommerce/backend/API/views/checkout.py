from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product, Address, Order, Purchase, Card, Vendor
from ..serializers import checkout_product_serializer, checkout_shopping_cart_serializer, address_serializer, shopping_cart_serializer
from ..utils import authentication, order_status


'''
This function control 'checkout/details' endpoint and sends information about prices in shopping cart.
@param
request: includes information about user, name(just for POST method), method
@return
Response(GET):  includes all prices such as products_price, delivery_price, discount and total_price in shopping cart
'''
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
        if serializer.get("product")["is_deleted"] == True:
            continue
        else:
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


'''
This function control 'checkout/payment' endpoint and add shopping cart items, card and address to Purchase table.
@param
request: includes information about user, address_id, method, card_id
@return
Response(POST):  includes successfull serializer(successfull value and message)
'''
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def checkout_payment(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]
    control = True

    if user.is_verified == False:
        return Response({'status': { 'successful': False, 'message': "Please verify your mail account."}})

    address_id = request.data["address_id"]
    address = Address.objects.filter(id=address_id)
    address_s = address_serializer.AddressResponseSerializer(address)
    card_id = request.data["card_id"]
    card = Card.objects.filter(id=card_id)

    items = ShoppingCartItem.objects.filter(customer_id=user.pk)
    if len(items) == 0:
        return Response({'status': { 'successful': False, 'message': "Shopping Cart is empty."}})

    serializers = shopping_cart_serializer.ShoppingCartResponseSerializer(items, many=True)

    order = Order(user_id=user.pk, card_id=card_id)
    order.save()

    amount = 0
    unit_price = 0 

    for serializer in serializers.data:
        if serializer.get("product")["is_deleted"] == True:
            continue
        else:
            control = False
            amount = serializer.get("amount")
            product_id = serializer.get("product")["id"]
            unit_price = serializer.get("product")["price"]
            vendor_id = serializer.get("product")["vendor"]["id"] # returns user_id from ProductResponseSerializer
            vendor_id = Vendor.objects.get(user_id=vendor_id).id # get vendor_id for Purchase table
            status = order_status.OrderStatus.ACCEPTED.value
            purchase = Purchase(product_id=product_id, amount=amount, unit_price=unit_price, status=status,\
                                address_id=address_id, vendor_id=vendor_id, order_id=order.pk)
            purchase.save()

    if control:
        order.delete()
        return Response({'status': { 'successful': False, 'message': "Payment process is not succesfully satisfied."}})
    
    items.delete()

    return Response({'status': { 'successful': True, 'message': "Payment process is successfully satisfied."}})


'''
This function control 'checkout/cancelorder/<int:id>' endpoint and change order_status to CANCELLED if it is ACCEPTED
@param
request: includes information about user, address_id, method, card_id
id:     includes order id which want to be
@return
Response(POST):  includes successfull serializer(successfull value and message)
'''
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def checkout_cancel_order(request, id):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)

    purchases = Purchase.objects.filter(order_id=int(id))
    for purchase in purchases:
        if purchase.status == order_status.OrderStatus.ACCEPTED.value:
            purchase.status = order_status.OrderStatus.CANCELLED.value
            purchase.save()
        else:
            continue

    return Response({'status': { 'successful': True, 'message': "Order is successfully deleted."}})
