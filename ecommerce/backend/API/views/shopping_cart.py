from rest_framework import exceptions
    return Response(sci_serializer.data)
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from API.utils import permissions, Role
from API.models import User, ShoppingCartItem
from API.serializers import shopping_cart_item_serializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
    sc_items = ShoppingCartItem.objects.filter(customer_id=id).values('id', 'product_id', 'amount')
def list_shopping_cart(request, id): #userId
    if User.objects.filter(id=id).first() is None:
    # if user not found
        return Response(status=status.HTTP_400_BAD_REQUEST)
    sci_serializer = shopping_cart_item_serializer.ShoppingCartItemSerializer(sc_items, many=True)