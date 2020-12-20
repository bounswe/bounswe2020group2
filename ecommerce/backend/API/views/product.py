from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes, renderer_classes
from rest_framework import status
import json
import base64
from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory, Image, Brand
from ..serializers.product_serializer import ProductResponseSerializer
from ..serializers import VendorProductResponseSerializer
from wsgiref.util import FileWrapper
from ..utils import ImageRenderer
from ..utils import validate_product_add_request

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

@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def save_image_test(request):
    base64_img = request.data["image"]
    decoded = base64.b64decode(base64_img)
    image = Image(image=decoded)
    image.save()
    return Response(status=status.HTTP_200_OK)


@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
@renderer_classes([ImageRenderer])
def get_image(request, image_id):
    image = Image.objects.get(pk=image_id).image
    return Response(image, content_type="image/jpg")



@api_view(['POST', 'PUT', 'DELETE'])
@permission_classes([permissions.IsVendorUser])
def vendor_product(request):
    
    if request.method == 'POST':
        validation_result = validate_product_add_request(request.data)
        if not validation_result[0]:
            response = VendorProductResponseSerializer(
            Product(),
            context = { 'is_successful': False,
                                'message': validation_result[1]}
            )

            return Response(response.data)
        
        vendor = Vendor.objects.filter(user=request.user).first()
        brand = Brand.objects.filter(pk=request.data["brand_id"]).first()
        subcategory = Subcategory.objects.filter(pk=request.data["subcategory_id"]).first()
        
        if brand is None or vendor is None or subcategory is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        discount = request.data["discount"]
        if discount is None:
            discount = 0
        new_product = Product(name=request.data["name"], subcategory=subcategory, price=request.data["price"],
            vendor=vendor, brand=brand,stock_amount=request.data["stock_amount"], discount=discount,
            short_description=request.data["short_description"], long_description=request.data["long_description"])
        new_product.save()

        index = 0
        for image_b64 in request.data["images"]:
            img_array = base64.b64decode(image_b64)
            image = Image(image=img_array)
            image.save()
            image_url = ImageUrls(product=new_product, image_url="/image/"+str(image.pk), index = index)
            image_url.save()
            index +=1

        response = VendorProductResponseSerializer(
            new_product,
            context = { 'is_successful': True,
                                'message': "Success"}
            )

        return Response(response.data)

    if request.method == 'DELETE':
        product_id = request.data["id"]
        vendor = Vendor.objects.filter(user=request.user).first()
        if product_id is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        product = Product.objects.filter(pk=product_id).filter(is_deleted=False).first()
        if product is None:
            response = VendorProductResponseSerializer(
            Product(),
            context = { 'is_successful': False,
                                'message': "Product not found"}
            )
            return Response(response.data)

        if product.vendor != vendor:
            return Response(status=status.HTTP_403_FORBIDDEN)
        
        product.is_deleted = True
        product.save()

        response = VendorProductResponseSerializer(
            product,
            context = { 'is_successful': True,
                                'message': "Success"}
            )
        return Response(response.data)
    
    if request.method == 'PUT':
        product_id = request.data["id"]
        vendor = Vendor.objects.filter(user=request.user).first()
        if product_id is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        product = Product.objects.filter(pk=product_id).filter(is_deleted=False).first()
        if product is None:
            response = VendorProductResponseSerializer(
            Product(),
            context = { 'is_successful': False,
                                'message': "Product not found"}
            )
            return Response(response.data)

        if product.vendor != vendor:
            return Response(status=status.HTTP_403_FORBIDDEN)

        validation_result = validate_product_add_request(request.data)
        if not validation_result[0]:
            response = VendorProductResponseSerializer(
            Product(),
            context = { 'is_successful': False,
                                'message': validation_result[1]}
            )

            return Response(response.data)
        
        brand = Brand.objects.filter(pk=request.data["brand_id"]).first()
        subcategory = Subcategory.objects.filter(pk=request.data["subcategory_id"]).first()
        if brand is None or subcategory is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        discount = request.data["discount"]
        if discount is None:
            discount = 0
        product.name = request.data["name"]
        product.price = request.data["price"]
        product.short_description = request.data["short_description"]
        product.long_description = request.data["long_description"]
        product.discount = discount
        product.stock_amount = request.data["stock_amount"]
        product.brand = brand
        product.subcategory = subcategory
        product.save()
        response = VendorProductResponseSerializer(
            product,
            context = { 'is_successful': True,
                                'message': "Success"}
            )
        return Response(response.data)
