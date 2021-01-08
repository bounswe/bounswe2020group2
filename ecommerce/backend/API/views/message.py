from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Conversation, Message
from ..serializers.message_serializer import *

# serves GET, POST requests for the given customer_id
@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_messages(request):
    #sender = request.user
    sender = User.objects.get(id=9)
    # get all conversations
    if request.method == 'GET':
        # get all non-deleted messages of the user
        conversations = Conversation.objects.filter(sender_id=sender.pk)
        conversation_serializer = ConversationSerializer(conversations, many=True)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'conversations': conversation_serializer.data})
    # add message
    elif request.method == 'POST':
        serializer = MessageRequestSerializer(data=request.data)
        # check if the formatted data is valid
        if serializer.is_valid():
            # create a Message object from the formatted data, and save it to the database
            receiver_id = serializer.validated_data.get("receiver_id")
            receiver = User.objects.get(id=receiver_id)
            if not Conversation.objects.filter(sender_id=sender.pk).filter(receiver_id=receiver_id):
                conversation = Conversation(sender=sender, receiver=receiver)
                conversation.save()
            else:
                conversation = Conversation.objects.get(sender_id=sender.pk, receiver_id=receiver_id)
            text = serializer.validated_data.get("text")
            attachment_url = serializer.validated_data.get("attachment_url")
            message = Message(conversation=conversation, receiver=receiver, text=text, attachment_url=attachment_url)
            message.save()
            return Response({'message_id': message.id, 'status': {'successful': True, 'message': "Message is successfully added"}})
    
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
