from API.models.user import Customer
from rest_framework import serializers
from ..models import Customer, Address

class AddressResponseSerializer(serializers.ModelSerializer):
    first_name = serializers.SerializerMethodField('get_first_name')
    last_name = serializers.SerializerMethodField('get_last_name')
    phone = serializers.SerializerMethodField('get_phone')
    
    class Meta:
        model = Address
        fields = ('id', 'title', 'address', 'first_name', 'last_name', 'province', 'city', 'country', 'phone', 'zip_code')

    def get_first_name(self, obj):
        return Customer.objects.filter(user_id=obj.user_id).first().first_name
    def get_last_name(self, obj):
        return Customer.objects.filter(user_id=obj.user_id).first().last_name
    def get_phone(self, obj):
        return { 'country_code': obj.phone_country_code, 'number': obj.phone_number}

class PhoneSerializer(serializers.Serializer):
    country_code = serializers.CharField()
    number = serializers.CharField()
    
class AddressRequestSerializer(serializers.Serializer):
    title = serializers.CharField()
    address = serializers.CharField()
    province = serializers.CharField()
    city = serializers.CharField()
    country = serializers.CharField()
    phone = PhoneSerializer()
    zip_code = serializers.CharField()
