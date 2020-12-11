from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory
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
    brand_id = product.brand_id
    subcategory_id = product.subcategory_id
    subcategory = Subcategory.objects.filter(id=subcategory_id).first()
    category = Category.objects.filter(id=subcategory.category_id).first()
    category_name = category.name
    category_id = category.id

    images = ImageUrls.objects.filter(product_id=product.id).values('image_url')
    image_list = []

    for image in images:
        image_list.append(image['image_url'])

    vendor = Vendor.objects.filter(id=v_id).first()
    if vendor.rating_count != 0:
        vendor_rating = vendor.total_rating/vendor.rating_count
    else:
        vendor_rating = None

    serializer = ProductSerializer(product_query, many=True)
    returnData = serializer.data[0]

    #calculate rating
    total_rating = returnData['total_rating']
    rating_count = returnData['rating_count']

    if rating_count != 0:
        rating = total_rating/rating_count
        returnData['rating']=rating 
    else:
        returnData['rating']=None
    
    brand_name= returnData['brand']
    returnData['brand']={'name':brand_name,'id':brand_id}

    subcategory_name = returnData['subcategory']
    returnData['subcategory']={'name':subcategory_name,'id':subcategory_id}

    vendor_name = returnData['vendor']
    returnData['vendor']={'rating':vendor_rating,'id':v_id,'name':vendor_name}

    returnData['images']=image_list

    old_price = returnData['price']

    price = old_price*(1-returnData['discount'])

    returnData['price'] = int(price)

    returnData['old_price'] = old_price

    returnData['category'] ={'id':category_id,'name':category_name}

    
    return Response(returnData)

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def homepage_products(request,no):
    products = Product.objects.all()[:no]
    serializer = ProductSerializer(products, many=True)
    return Response(serializer.data)
