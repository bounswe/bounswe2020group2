from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser

from API.utils import permissions, Role
from API.utils.jwttoken import generate_access_token
from API.utils.crypto import Crypto
from API.models import Product
from API.serializers import account_serializer

from API.serializers import ProductSerializer 

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def homepage_products(request):

    products = Product.objects.all()[:20]
    serializer = ProductSerializer(products, many=True)

    return Response(serializer.data)