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
from ..utils import notifyPriceChange

# returns the details of the product having the given product_id
@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def get_product_detail(request, product_id):
    # return 400_BAD_REQUEST if product is not found
    if Product.objects.filter(id=product_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    # filter products that are not soft deleted and have the given product_id
    product = Product.objects.filter(id=product_id).filter(is_deleted=False).first()
    if product is None:
        return Response({'successful': False, 'message': "No such product is found"})
    # serialize it as a single json object
    product = ProductResponseSerializer(product)
    return Response(product.data)

# returns the given number(num) of non-deleted products
@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def get_homepage_products(request, num):
    # get all the products that are non-deleted and select according to the given num variable
    products = Product.objects.filter(is_deleted=False)[:num]
    # serialize them as a json array
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
        
        brand = None
        brand_data = request.data.get('brand_id')
        if brand_data is None:
            return Response(
                VendorProductResponseSerializer(
                    Product(),
                    context = { 'is_successful': False,
                                'message': "Brand value cannot be empty"}
                )
            )
        if isinstance(brand_data, str):
            brand = Brand(name=brand_data)
            brand.save()
        else:
            brand = Brand.objects.filter(pk=int(brand_data)).first()

        vendor = Vendor.objects.filter(user=request.user).first()
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
        
        brand = None
        brand_data = request.data.get('brand_id')
        if brand_data is None:
            return Response(
                VendorProductResponseSerializer(
                    Product(),
                    context = { 'is_successful': False,
                                'message': "Brand value cannot be empty"}
                )
            )
        if isinstance(brand_data, str):
            brand = Brand(name=brand_data)
            brand.save()
        else:
            brand = Brand.objects.filter(pk=int(brand_data)).first()
        subcategory = Subcategory.objects.filter(pk=request.data["subcategory_id"]).first()
        if brand is None or subcategory is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        previous_price = product.price * (1 - product.discount)
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

        for image_url in request.data["image_urls_delete"]:
            ImageUrls.objects.filter(image_url=image_url).first().delete()

        index_image_url = ImageUrls.objects.filter(product=product).order_by('-index').first()
        index = index_image_url.image_url if index_image_url is not None else None
        for image_b64 in request.data["images"]:
            img_array = base64.b64decode(image_b64)
            image = Image(image=img_array)
            image.save()
            image_url = ImageUrls(product=new_product, image_url="/image/"+str(image.pk), index = index)
            image_url.save()
            index += 1

        product.save()

        new_price = product.price * (1 - product.discount)

        if(new_price < previous_price):
            notifyPriceChange(product, new_price, previous_price)
        
        response = VendorProductResponseSerializer(
            product,
            context = { 'is_successful': True,
                                'message': "Success"}
            )
        return Response(response.data)
