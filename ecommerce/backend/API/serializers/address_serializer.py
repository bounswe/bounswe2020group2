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

    def create(self, validated_data):
            obj = Address(**validated_data)
            obj.save()
            return obj
    def update(self, instance, validated_data):
        instance.save()
        return instance
"""
class AddressRequestSerializer(serializers.ModelSerializer):
    phone_country_code = serializers.SerializerMethodField('get_phone_country_code')
    phone_number = serializers.SerializerMethodField('get_phone_number')

    class Meta:
        model = Address
        fields = ('title', 'address', 'province', 'city', 'country', 'phone_country_code', 'phone_number', 'zip_code')

    def get_phone_country_code(self, obj):
        print("qqqqqqq")
        print(obj.phone)
        return obj.phone.phone_country_code
    def get_phone_number(self, obj):
        return obj.phone.phone_number
"""