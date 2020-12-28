from rest_framework import serializers
from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor
from ..serializers.product_serializer import ProductResponseSerializer

# Formats the Shopping Cart Item objects taken from the database for GET requests and adds the details of the product in place of product_id
class ShoppingCartResponseSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')

    class Meta:
        model = ShoppingCartItem
        fields = ('id', 'amount', 'product')

    def get_product(self, obj):
        product = Product.objects.filter(id=obj.product_id).first()
        return ProductResponseSerializer(product).data

# Formats the body of the POST request to make it compatible with the Shopping Cart Item model in the database
class ShoppingCartRequestSerializer(serializers.Serializer):
    product_id = serializers.IntegerField()
    amount = serializers.IntegerField()
