from API.models.user import Customer
from rest_framework import serializers
from ..models import User, Message

# Formats the Message objects taken from the database for GET requests and combines the phone columns into a single object.
class MessageResponseSerializer(serializers.ModelSerializer):
    sent_by_me = serializers.SerializerMethodField('get_sent_by_me')
    class Meta:
        model = Message
        fields = ('id', 'text', 'sent_by_me', 'date', 'attachment_url')
    
    def get_sent_by_me(self, obj):
        return obj.sender == self.context["sender"]

# Formats the phone object in the body of the POST request into two columns to make it compatible with the database
class PhoneSerializer(serializers.Serializer):
    country_code = serializers.CharField()
    number = serializers.CharField()
    
# Formats the body of the POST request to make it compatible with the Message model in the database
class MessageRequestSerializer(serializers.Serializer):
    receiver_id = serializers.IntegerField()
    text = serializers.CharField()
    attachment_url = serializers.CharField()
