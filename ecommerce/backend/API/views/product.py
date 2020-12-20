from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory
from ..serializers.product_serializer import ProductResponseSerializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def get_product_detail(request, product_id):
    # if product not found
    if Product.objects.filter(id=product_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    product = Product.objects.filter(id=product_id).first()
    if product is None:
        return Response({'successful': False, 'message': "No such product is found"})
    product = ProductResponseSerializer(product)
    return Response(product.data)


@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def get_homepage_products(request, num):
    products = Product.objects.all()[:num]
    serializer = ProductResponseSerializer(products, many=True)
    return Response(serializer.data)
