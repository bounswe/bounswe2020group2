from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers import shopping_cart_serializer, shopping_cart_item_serializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def list_shopping_cart(request, id): #userId
    # if user not found
    if User.objects.filter(id=id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    sc_items = ShoppingCartItem.objects.filter(customer_id=id).values('id', 'product_id', 'amount')
    sci_serializer = shopping_cart_item_serializer.ShoppingCartItemSerializer(sc_items, many=True)
    return Response(sci_serializer.data)


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def add_shopping_cart_item(request, id):
    serializer = shopping_cart_serializer.ShoppingCartRequestSerializer(data=request.data)
    if not serializer.is_valid():
        return Response("Invalid input")

    # userId = serializer.validated_data.get("userId")
    # if userId != int(id):
    #     return Response("Invalid user")

    user = User.objects.filter(pk=int(id)).first()
    amount = serializer.validated_data.get("amount")
    product_id = serializer.validated_data.get("productId")
    product = Product.objects.filter(pk=product_id).first()
    stock_amount = product.stock_amount
    shopping_cart = ShoppingCartItem.objects.filter(customer_id=user.pk).filter(product_id=product_id).first()

    if shopping_cart is None:
        shopping_cart = ShoppingCartItem(customer=user, product_id=product_id, amount=amount)
    else:
        shopping_cart.amount+=amount
        
    if shopping_cart.amount > stock_amount:
            return Response("Stock Amount is reached")
    elif shopping_cart.amount < 0:
        return Response("Amount cannot be negative")
    shopping_cart.save()

    return Response("Success")