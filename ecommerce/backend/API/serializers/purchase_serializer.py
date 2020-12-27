from rest_framework import serializers

from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor, Order, Purchase, Address
from ..serializers.product_serializer import ProductResponseSerializer
from ..serializers.address_serializer import AddressResponseSerializer

class PurchaseResponseSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')
    vendor = serializers.SerializerMethodField('get_vendor')
    address = serializers.SerializerMethodField('get_address')
    status = serializers.SerializerMethodField('get_status')

    class Meta:
        model = Purchase
        fields = ('id', 'amount', 'product', 'status', 'unit_price', 'purchase_date', 'vendor', 'address')

    def get_product(self, obj):
        product = Product.objects.filter(id=obj.product.id).first()
        return ProductResponseSerializer(product).data

    def get_vendor(self, obj):
        vendor = Vendor.objects.filter(id=obj.vendor.id).first()
        vendor_full_name = vendor.first_name + " " + vendor.last_name
        if vendor.total_rating != 0 and vendor.rating_count != 0:
            vendor_rating = vendor.total_rating / vendor.rating_count
        else:
            vendor_rating = None
        return { 'id': vendor.id, 'name': vendor_full_name, 'rating': vendor_rating }

    def get_address(self, obj):
        address = Address.objects.filter(id=obj.address.id).first()
        return AddressResponseSerializer(address).data

    def get_status(self, obj):
        status = obj.status
        if status == -1:
            return 'cancelled'
        elif status == 0:
            return 'accepted'
        elif status == 1:
            return 'at_cargo'
        elif status == 2:
            return 'delivered'        
            