from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from API.utils import permissions, Role
from API.models import User, ShoppingCartItem
from API.serializers import shopping_cart_item_serializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def list_shopping_cart(request, id): #userId
    # if user not found
    if User.objects.filter(id=id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    sc_items = ShoppingCartItem.objects.filter(customer_id=id).values('id', 'product_id', 'amount')
    sci_serializer = shopping_cart_item_serializer.ShoppingCartItemSerializer(sc_items, many=True)
    return Response(sci_serializer.data)