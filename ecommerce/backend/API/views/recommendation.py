from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json
import random

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory,Purchase, Order, User
from ..serializers.address_serializer import AddressResponseSerializer
from ..serializers.product_serializer import ProductResponseSerializer
from ..serializers.purchase_serializer import PurchaseResponseSerializer
from ..utils.order_status import OrderStatus

@api_view(['GET'])
@permission_classes([permissions.IsCustomerUser])
def recommend_products(request):

    # get user
    user = User.objects.filter(user=request.user).first()
    user_id = user.id

    # define subcategories that this user purchased a product from
    purchased_subcategories = []

    # products to recommend
    recommended_products = []

    # products that i bought
    my_products = []

    all_purchases = Purchase.objects.all()

    for purchase in all_purchases:

        # if this purchase is my purchase 
        if purchase.order.user.id == user_id:

            # add this purchased product to the my_products list
            my_products.append(purchase.product)

            # if purchase subcategory is not already in the list, add this subcategory to the purchased_subcategories
            if purchase.product.subcategory_id not in purchased_subcategories:

                purchased_subcategories.append(purchase.product.subcategory_id)

    # add a "random product that our user has not bought" to the "list of recommended products" from the "subcategory that our user has bought a product from"
    for subcategory in purchased_subcategories:

        products = Product.objects.filter(subcategory_id = subcategory,is_deleted=False).all()

        products_I_didnt_buy = []

        for product in products:

            # if our user did not buy this product, add it to the products_I_didnt_buy list
            if product not in my_products:

                products_I_didnt_buy.append(product)

        product_from_this_subcategory = random.choice(products_I_didnt_buy)

        serializer = ProductResponseSerializer(product_from_this_subcategory)
        serialized_product = serializer.data

        recommended_products.append(serialized_product)

    # if recommended_products list is empty
    if(len(recommended_products)==0):
        # get 5 products that are non-deleted 
        products = Product.objects.filter(is_deleted=False)[:5]

        for product in products:
            serializer = ProductResponseSerializer(product)
            serialized_product = serializer.data

            recommended_products.append(serialized_product)

    return Response({'products':recommended_products})