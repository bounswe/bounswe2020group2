from rest_framework import serializers
from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor

class ProductSerializer(serializers.ModelSerializer):
    subcategory = serializers.SerializerMethodField('get_subcategory')
    category = serializers.SerializerMethodField('get_category')
    brand = serializers.SerializerMethodField('get_brand')
    vendor = serializers.SerializerMethodField('get_vendor')
    class Meta:
        model = Product
        fields = ('id', 'name', 'price', 'creation_date','total_rating', 
            'rating_count','stock_amount','short_description','subcategory','long_description','discount','category','brand','vendor')

    def get_subcategory(self, obj):
        subcategory_obj = Subcategory.objects.filter(id=obj.subcategory.id).first()
        subcategory_name = subcategory_obj.name
        return subcategory_name
    
    def get_category(self, obj):
        subcategory_obj = Subcategory.objects.filter(id=obj.subcategory.id).first()
        category_id = subcategory_obj.category.id
        category_name = Category.objects.filter(id=category_id).first().name
        return category_name
    
    def get_brand(self, obj):
        brand_name = Brand.objects.filter(id=obj.brand.id).first().name
        return brand_name
    
    def get_vendor(self, obj):
        vendor = Vendor.objects.filter(id=obj.vendor.id).first()
        vendor_full_name = vendor.first_name + " " + vendor.last_name
        return vendor_full_name