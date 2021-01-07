from API.models.user import Customer
from rest_framework import serializers
from ..models import User, Message

# Formats the Address objects taken from the database for GET requests and combines the phone columns into a single object.
class MessageResponseSerializer(serializers.ModelSerializer):
    phone = serializers.SerializerMethodField('get_phone')
    
    class Meta:
        model = Address
        fields = ('id', 'title', 'name', 'surname', 'address', 'province', 'city', 'country', 'phone', 'zip_code')

    def get_phone(self, obj):
        return { 'country_code': obj.phone_country_code, 'number': obj.phone_number}

# Formats the phone object in the body of the POST request into two columns to make it compatible with the database
class PhoneSerializer(serializers.Serializer):
    country_code = serializers.CharField()
    number = serializers.CharField()
    
# Formats the body of the POST request to make it compatible with the Address model in the database
class MessageRequestSerializer(serializers.Serializer):
    title = serializers.CharField()
    name = serializers.CharField()
    surname = serializers.CharField()
    address = serializers.CharField()
    province = serializers.CharField()
    city = serializers.CharField()
    country = serializers.CharField()
    phone = PhoneSerializer()
    zip_code = serializers.CharField()
