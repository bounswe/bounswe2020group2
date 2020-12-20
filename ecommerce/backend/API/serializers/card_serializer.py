from API.models.user import Customer
from rest_framework import serializers
from ..models import Customer, Card

class CardResponseSerializer(serializers.ModelSerializer):
    expiration_date = serializers.SerializerMethodField('get_expiration_date')

    class Meta:
        model = Card
        fields = ('id', 'name', 'owner_name', 'serial_number', 'expiration_date', 'cvv')

    def get_expiration_date(self, obj):
        return { 'month': obj.expiration_month, 'year': obj.expiration_year}

class ExpirationSerializer(serializers.Serializer):
    month = serializers.CharField()
    year = serializers.CharField()
    
class CardRequestSerializer(serializers.Serializer):
    name = serializers.CharField()
    owner_name = serializers.CharField()
    serial_number = serializers.CharField()
    expiration_date = ExpirationSerializer()
    cvv = serializers.IntegerField()