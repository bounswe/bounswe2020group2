from API.serializers.account_serializer import UserSerializer
from rest_framework import serializers
from ..models import User, Message, Customer, Vendor
from ..utils import Role

# Formats the Message objects taken from the database for GET requests
class MessageResponseSerializer(serializers.ModelSerializer):
    sent_by_me = serializers.SerializerMethodField('get_sent_by_me')
    class Meta:
        model = Message
        fields = ('id', 'text', 'sent_by_me', 'date', 'attachment_url')
    
    def get_sent_by_me(self, obj):
        return obj.sender == self.context["sender"]

# Formats the Conversation objects taken from the database for GET requests
class ConversationSerializer(serializers.ModelSerializer):
    counterpart = serializers.SerializerMethodField('get_counterpart')
    messages = serializers.SerializerMethodField('get_messages')
    class Meta:
        model = Message
        fields = ('counterpart', 'messages')
    
    # returns the details of the user that sender is in contact with
    def get_counterpart(self, obj):
        counterpart = obj.user1 if self.context["sender"].id == obj.user2.id else obj.user2
        full_name = counterpart.role
        if counterpart.role == Role.CUSTOMER.value:
            customer = Customer.objects.filter(user=counterpart).first()
            full_name = customer.first_name + " " + customer.last_name
        elif counterpart.role == Role.VENDOR.value:
            vendor = Vendor.objects.filter(user=counterpart).first()
            full_name = vendor.first_name + " " + vendor.last_name
        return {'id': counterpart.id, 'name': full_name}

    # returns all messages in the conversation
    def get_messages(self, obj):
        messages = Message.objects.filter(conversation=obj).order_by('date') # order by date
        serializer = MessageResponseSerializer(messages, context={'sender': self.context["sender"]}, many=True)
        return serializer.data
    
# Formats the body of the POST request to make it compatible with the Message model in the database
class MessageRequestSerializer(serializers.Serializer):
    receiver_id = serializers.IntegerField()
    text = serializers.CharField()
    attachment = serializers.CharField(allow_null = True)
