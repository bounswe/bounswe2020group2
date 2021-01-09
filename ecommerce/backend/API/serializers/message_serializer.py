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
    counterpart = serializers.SerializerMethodField('get_counterpart')
    messages = serializers.SerializerMethodField('get_messages')
    class Meta:
        model = Message
        fields = ('counterpart', 'messages')
    
    def get_counterpart(self, obj):
        # all objects in the objects list has the same receiver_id
        counterpart_id = obj.user1.id if self.context["sender"].id == obj.user2.id else obj.user2.id
        user_serializer = UserSerializer(User.objects.get(id=counterpart_id))
        full_name = user_serializer.data.get("first_name") + " " + user_serializer.data.get("last_name")
        return {'id': counterpart_id, 'name': full_name}
    
    def get_messages(self, obj):
        messages = Message.objects.filter(conversation=obj)
        serializer = MessageResponseSerializer(messages, context={'sender': self.context["sender"]}, many=True)
        return serializer.data
    
# Formats the body of the POST request to make it compatible with the Message model in the database
class MessageRequestSerializer(serializers.Serializer):
    receiver_id = serializers.IntegerField()
    text = serializers.CharField()
    attachment_url = serializers.CharField()
