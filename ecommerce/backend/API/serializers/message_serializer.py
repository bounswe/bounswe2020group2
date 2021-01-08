from API.serializers.account_serializer import UserSerializer
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

class ConversationSerializer(serializers.ModelSerializer):
    receiver = serializers.SerializerMethodField('get_receiver')
    messages = serializers.SerializerMethodField('get_messages')
    class Meta:
        model = Message
        fields = ('receiver', 'messages')
    
    def get_receiver(self, obj):
        # all objects in the objects list has the same receiver_id
        user_serializer = UserSerializer(User.objects.get(obj.receiver.id))
        if user_serializer.is_valid():
            full_name = user_serializer.validated_data.get("first_name") + " " 
            + user_serializer.validated_data.get("last_name")
            return {'id': obj["receiver_id"], 'name': full_name}
    
    def get_messages(self, obj):
        messages = Message.objects.filter(sender_id=obj["sender_id"]).filter(receiver_id=obj["receiver_id"])
        serializer = MessageResponseSerializer(messages, context={'sender': obj["sender_id"]}, many=True)
        return serializer.data
    
# Formats the body of the POST request to make it compatible with the Message model in the database
class MessageRequestSerializer(serializers.Serializer):
    receiver_id = serializers.IntegerField()
    text = serializers.CharField()
    attachment_url = serializers.CharField()
