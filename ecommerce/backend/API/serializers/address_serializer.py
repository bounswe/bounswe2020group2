from API.models.user import Customer
from rest_framework import serializers
from ..models import Customer, Address

class AddressSerializer(serializers.ModelSerializer):
    first_name = serializers.SerializerMethodField('get_first_name')
    last_name = serializers.SerializerMethodField('get_first_name')
    class Meta:
        model = Address
        fields = ('id', 'title', 'phone_number', 'first_name, last_name', 'address', 'province', 'city', 'country')

    def get_first_name(self, obj):
        return Customer.objects.filter(user_id=obj.user.id).first().first_name
    def get_last_name(self, obj):
        return Customer.objects.filter(user_id=obj.user.id).first().last_name
