from API.models.product import ImageUrls
from rest_framework import serializers
from ..models import Vendor

class VendorResponseSerializer(serializers.ModelSerializer):
    title = serializers.SerializerMethodField('get_title')
    rating = serializers.SerializerMethodField('get_rating')
    class Meta:
        model = Vendor
        fields = ('id', 'title', 'rating')

    def get_title(self, obj):
        f = obj.first_name
        l = obj.last_name
        return f + " " + l

    def get_rating(self, obj):
        total_rating = obj.total_rating
        rating_count = obj.rating_count
        if total_rating != 0 and rating_count != 0:
            return total_rating / rating_count
        else:
            return None
