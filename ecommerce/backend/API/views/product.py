from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes, renderer_classes
from rest_framework import status
import json
import base64
from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory, Image, Brand
from ..serializers.product_serializer import ProductSerializer
from ..serializers import VendorProductResponseSerializer
from wsgiref.util import FileWrapper
from ..utils import ImageRenderer
from ..utils import validate_product_add_request

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def product_detail(request, productId):
    # if product not found
    if Product.objects.filter(id=productId).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    product_query = Product.objects.filter(id=productId)

    product = Product.objects.filter(id=productId).first()
    if product.is_deleted:
        return Response(status=status.HTTP_404_NOT_FOUND)
    v_id = product.vendor_id
    brand_id = product.brand_id
    subcategory_id = product.subcategory_id
    subcategory = Subcategory.objects.filter(id=subcategory_id).first()
    category = Category.objects.filter(id=subcategory.category_id).first()
    category_name = category.name
    category_id = category.id

    images = ImageUrls.objects.filter(product_id=product.id).order_by('index').values('image_url')
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
        

        