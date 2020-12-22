from API.models.user import Customer
from rest_framework import serializers
from ..models import Customer, Address

class AddressResponseSerializer(serializers.ModelSerializer):
    phone = serializers.SerializerMethodField('get_phone')
    
    class Meta:
        model = Address
        fields = ('id', 'title', 'name', 'surname', 'address', 'province', 'city', 'country', 'phone', 'zip_code')

    def get_phone(self, obj):
        return { 'country_code': obj.phone_country_code, 'number': obj.phone_number}

class PhoneSerializer(serializers.Serializer):
    country_code = serializers.CharField()
    number = serializers.CharField()
    
class AddressRequestSerializer(serializers.Serializer):
    title = serializers.CharField()
    name = serializers.CharField()
    surname = serializers.CharField()
    address = serializers.CharField()
    province = serializers.CharField()
    city = serializers.CharField()
    country = serializers.CharField()
    phone = PhoneSerializer()
    zip_code = serializers.CharField()
