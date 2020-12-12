from rest_framework import serializers
from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor
from ..serializers.checkout_product_serializer import CheckoutProductSerializer

class CheckoutShoppingCartSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')
    class Meta:
        model = ShoppingCartItem
        fields = ('id', 'amount', 'product')
    def get_product(self, obj):
        product = Product.objects.filter(id=obj['product_id']).first()
        return CheckoutProductSerializer(product).data