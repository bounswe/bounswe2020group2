from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers import checkout_product_serializer, checkout_shopping_cart_serializer
from ..utils import authentication


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def user_verify(request):
    return Response(status=status.HTTP_403_FORBIDDEN)