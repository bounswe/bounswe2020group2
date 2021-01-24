from API.models.product import ImageUrls
from rest_framework import serializers
from ..models import ShoppingCartItem, Product, Subcategory, Category, Brand, Vendor

class ProductResponseSerializer(serializers.ModelSerializer):
    price_after_discount = serializers.SerializerMethodField('get_price_after_discount')
    rating = serializers.SerializerMethodField('get_rating')
    subcategory = serializers.SerializerMethodField('get_subcategory')
    category = serializers.SerializerMethodField('get_category')
    brand = serializers.SerializerMethodField('get_brand')
    vendor = serializers.SerializerMethodField('get_vendor')
    images = serializers.SerializerMethodField('get_images')
    class Meta:
        model = Product
        fields = ('id', 'name', 'creation_date', 'discount', 'price', 'price_after_discount', 'total_rating', 
            'rating_count', 'rating', 'stock_amount', 'short_description', 'long_description', 'category', 'subcategory','brand','vendor', 'images', 'is_deleted')

    def get_price_after_discount(self, obj):
        price = obj.price
        discount = obj.discount
        if price != 0.0 and discount != 0.0:
            return price * (1 - discount)
        else:
            return None

    def get_rating(self, obj):
        total_rating = obj.total_rating
        rating_count = obj.rating_count
        if total_rating != 0 and rating_count != 0:
            return total_rating / rating_count
        else:
            return None
    
    def get_subcategory(self, obj):
        subcategory = Subcategory.objects.filter(id=obj.subcategory.id).first()
        return { 'id': subcategory.id, 'name': subcategory.name }
    
    def get_category(self, obj):
        subcategory_obj = Subcategory.objects.filter(id=obj.subcategory.id).first()
        category_id = subcategory_obj.category.id
        category = Category.objects.filter(id=category_id).first()
        return { 'id': category.id, 'name': category.name }
    
    def get_brand(self, obj):
        brand = Brand.objects.filter(id=obj.brand.id).first()
        return { 'id': brand.id, 'name': brand.name }
    
    def get_vendor(self, obj):
        vendor = Vendor.objects.filter(id=obj.vendor.id).first()
        vendor_full_name = vendor.first_name + " " + vendor.last_name
        if vendor.total_rating != 0 and vendor.rating_count != 0:
            vendor_rating = vendor.total_rating / vendor.rating_count
        else:
            vendor_rating = None
        return { 'id': vendor.user_id, 'name': vendor_full_name, 'rating': vendor_rating }
    
    def get_images(self, obj):
        image_urls = [obj.image_url for obj in ImageUrls.objects.filter(product_id=obj.id).all()]
        return image_urls

class VendorProductResponseSerializer(serializers.ModelSerializer):
    status = serializers.SerializerMethodField("get_status")
    
    class Meta:
        model = Product
        fields = ('id', 'status')

    def get_status(self, obj):
        is_successful = self.context.get("is_successful")
        message = self.context.get("message")
        return {
            'successful': is_successful,
            'message': message
        }