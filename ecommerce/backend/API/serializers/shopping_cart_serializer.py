from rest_framework import serializers
from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor
from ..serializers.product_serializer import ProductResponseSerializer

class ShoppingCartResponseSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')
    class Meta:
        model = ShoppingCartItem
        fields = ('id', 'amount', 'product')
    def get_product(self, obj):
        product = Product.objects.filter(id=obj.product_id).first()
        return ProductResponseSerializer(product).data

class ShoppingCartRequestSerializer(serializers.Serializer):
    product_id = serializers.IntegerField()
    amount = serializers.IntegerField()