from rest_framework import serializers
from ..models import Review

class ReviewSerializer(serializers.ModelSerializer):
    product_id = serializers.SerializerMethodField('get_product_id')
    vendor_id = serializers.SerializerMethodField('get_vendor_id')

    def get_product_id(self, obj):
        if obj.product is None:
            return None
        else:
            return obj.product.pk
    
    def get_vendor_id(self, obj):
        if obj.vendor is None:
            return None
        else:
            return obj.vendor.pk

    class Meta:
        model = Review
        fields = ('id', 'rating', 'comment', 'product_id', 'vendor_id', 'review_date')

class ReviewResponseSerializer(serializers.ModelSerializer):
    review = serializers.SerializerMethodField("get_review")
    status = serializers.SerializerMethodField("get_status")

    class Meta:
        model = Review
        fields = ('review', 'status')

    def get_review(self, obj):
        return ReviewSerializer(obj).data

    def get_status(self, obj):
        is_successful = self.context.get("is_successful")
        message = self.context.get("message")
        return {
            'is_successful': is_successful,
            'message': message
        }