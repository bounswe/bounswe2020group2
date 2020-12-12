from rest_framework import serializers
from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor

class CheckoutProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ('id', 'name', 'price')
