from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import Product
from ..serializers.product_serializer import ProductSerializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def product_detail(request, productId):
    # if product not found
    if Product.objects.filter(id=productId).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    product = Product.objects.filter(id=productId)
    serializer = ProductSerializer(product, many=True)
    return Response(serializer.data)

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def homepage_products(request,no):
    products = Product.objects.all()[:no]
    serializer = ProductSerializer(products, many=True)
    return Response(serializer.data)
