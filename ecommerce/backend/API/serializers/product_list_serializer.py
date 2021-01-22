from rest_framework import serializers

from ..models import Product, ProductListItem
from ..serializers.product_serializer import ProductResponseSerializer

class ProductListResponseSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')

    class Meta:
        model = ProductListItem
        fields = ('id', 'product')

    def get_product(self, obj):
        product = Product.objects.filter(id=obj.product.id).first()
        return ProductResponseSerializer(product).data
