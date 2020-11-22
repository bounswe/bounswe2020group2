from rest_framework import serializers
from API.models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor
from API.serializers.product_serializer import ProductSerializer

class ShoppingCartItemSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')
    class Meta:
        model = ShoppingCartItem
        fields = ('id', 'amount', 'product')
    def get_product(self, obj):
        product = Product.objects.filter(id=obj['product_id']).first()
        return ProductSerializer(product).data