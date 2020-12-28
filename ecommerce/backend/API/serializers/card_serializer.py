from API.models.user import Customer
from rest_framework import serializers
from ..models import Customer, Card

# Formats the Card objects taken from the database for GET requests and combines the expiration date columns into a single object.
class CardResponseSerializer(serializers.ModelSerializer):
    expiration_date = serializers.SerializerMethodField('get_expiration_date')

    class Meta:
        model = Card
        fields = ('id', 'name', 'owner_name', 'serial_number', 'expiration_date', 'cvv')

    def get_expiration_date(self, obj):
        return { 'month': obj.expiration_month, 'year': obj.expiration_year}

# Formats the expiration object in the body of the POST request into two columns to make it compatible with the database
class ExpirationSerializer(serializers.Serializer):
    month = serializers.CharField()
    year = serializers.CharField()

# Formats the body of the POST request to make it compatible with the Card model in the database
class CardRequestSerializer(serializers.Serializer):
    name = serializers.CharField()
    owner_name = serializers.CharField()
    serial_number = serializers.CharField()
    expiration_date = ExpirationSerializer()
    cvv = serializers.IntegerField()
