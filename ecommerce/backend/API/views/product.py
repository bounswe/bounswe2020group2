from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls
from ..serializers.product_serializer import ProductSerializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def product_detail(request, productId):
    # if product not found
    if Product.objects.filter(id=productId).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    product_query = Product.objects.filter(id=productId)

    product = Product.objects.filter(id=productId).first()
    v_id = product.vendor_id

    images = ImageUrls.objects.filter(product_id=product.id).values('image_url')
    image_list = []

    for image in images:
        image_list.append(image['image_url'])

    vendor = Vendor.objects.filter(id=v_id).first()
    if vendor.rating_count != 0:
        vendor_rating = vendor.total_rating/vendor.rating_count
    else:
        vendor_rating = 'not rated yet'

    serializer = ProductSerializer(product_query, many=True)
    returnData = serializer.data[0]

    #calculate rating
    total_rating = returnData['total_rating']
    rating_count = returnData['rating_count']

    if rating_count != 0:
        rating = total_rating/rating_count
        returnData['rating']=rating 
    else:
        returnData['rating']='not rated yet'

    returnData['vendor_rating']=vendor_rating

    returnData['images']=image_list
    
    return Response(returnData)

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def homepage_products(request,no):
    products = Product.objects.all()[:no]
    serializer = ProductSerializer(products, many=True)
    return Response(serializer.data)
