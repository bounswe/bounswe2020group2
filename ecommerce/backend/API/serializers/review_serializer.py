from rest_framework import serializers
from ..models import Review, Customer

class ReviewSerializer(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')
    vendor = serializers.SerializerMethodField('get_vendor')
    reviewed_by = serializers.SerializerMethodField('get_reviewed_by')

    def get_reviewed_by(self, obj):
        if obj.reviewed_by is None:
            return None
        customer = Customer.objects.filter(user=obj.reviewed_by).first()
        if customer is None:
            return None
        return {
            'id': obj.reviewed_by.id,
            'firstname': customer.first_name,
            'lastname': customer.last_name
        }

    def get_product(self, obj):
        if obj.product is None:
            return None
        else:
            return {
                'id': obj.product.pk,
                'name': obj.product.name
            }
    
    def get_vendor(self, obj):
        if obj.vendor is None:
            return None
        else:
            return {
                'id': obj.vendor.id,
                'firstname': obj.vendor.first_name,
                'lastname': obj.vendor.last_name
            }

    class Meta:
        model = Review
        fields = ('id', 'rating', 'comment', 'product', 'vendor', 'review_date', 'reviewed_by')

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
            'successful': is_successful,
            'message': message
        }

class ReviewResponseListSerializer(serializers.ModelSerializer):
    reviews = serializers.SerializerMethodField("get_reviews")
    status = serializers.SerializerMethodField("get_status")
    class Meta:
        model = Review
        fields = ('reviews', 'status')

    def get_reviews(self, obj):
        data = []
        for review in obj:
            data.append(ReviewSerializer(review).data)
        return data

    def get_status(self, obj):
        is_successful = self.context.get("is_successful")
        message = self.context.get("message")
        return {
            'successful': is_successful,
            'message': message}