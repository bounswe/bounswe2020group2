from rest_framework import serializers
from ..models import Message

# Formats the body of the POST request to make it compatible with the Message model in the database
class MessageRequestSerializer(serializers.Serializer):
    receiver_id = serializers.IntegerField()
    text = serializers.CharField()
    attachment = serializers.CharField(allow_null = True)

# Formats the Message objects taken from the database for GET requests
class MessageResponseSerializer(serializers.ModelSerializer):
    sent_by_me = serializers.SerializerMethodField('get_sent_by_me')
    class Meta:
        model = Message
        fields = ('id', 'text', 'sent_by_me', 'date', 'attachment_url')
    
    def get_sent_by_me(self, obj):
        return obj.sender == self.context["sender"]