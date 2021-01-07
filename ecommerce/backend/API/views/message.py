from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Message
from ..serializers.message_serializer import *

# serves GET, POST requests for the given customer_id
@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_messages(request):
    sender = request.user
    # get all messages
    if request.method == 'GET':
        # get all non-deleted messages of the user
        messages = Message.objects.filter(sender_id=sender.pk)
        # serialize them into a json array
        message_serializer = MessageResponseSerializer(messages, context={'sender': sender}, many=True)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'cards': message_serializer.data})
    # add message
    elif request.method == 'POST':
        serializer = MessageRequestSerializer(data=request.data)
        # check if the formatted data is valid
        if serializer.is_valid():
            # create a Message object from the formatted data, and save it to the database
            receiver = User.objects.get(serializer.validated_data.get("receiver_id"))
            text = serializer.validated_data.get("text")
            attachment_url = serializer.validated_data.get("attachment_url")
            message = Message(sender=sender, receiver=receiver, text=text, attachment_url=attachment_url)
            message.save()
            return Response({'message_id': message.id, 'status': {'successful': True, 'message': "Message is successfully added"}})
    
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
