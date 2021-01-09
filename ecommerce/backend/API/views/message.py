from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from django.db.models import Q
import base64

from ..utils import permissions, Role
from ..models import User, Conversation, Message, Image
from ..serializers.message_serializer import *

# serves GET, POST requests for the given customer_id
@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_messages(request):
    sender = request.user
    print(sender)
    print("aaaaa")
    #sender = User.objects.get(id=9)
    # get all conversations
    if request.method == 'GET':
        # get all non-deleted messages of the user
        conversations = Conversation.objects.filter(Q(user1=sender) | Q(user2=sender))
        conversation_finaltime_dict = {conv.id: Message.objects.filter(conversation=conv).latest('date').id for conv in conversations}
        conv_ids_latest_first = reversed(sorted(conversation_finaltime_dict, key=conversation_finaltime_dict.get))
        conversations = Conversation.objects.filter(Q(user1=sender) | Q(user2=sender)).filter(id__in=conv_ids_latest_first)
        conversation_serializer = ConversationSerializer(conversations, context={'sender':sender},many=True)
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
            conversation = None
            if not Conversation.objects.filter((Q(user1=sender) & Q(user2=receiver)) | (Q(user1=receiver) & Q(user2=sender))):
                conversation = Conversation(user1=sender, user2=receiver)
                conversation.save()
            else:
                conversation = Conversation.objects.filter((Q(user1=sender) & Q(user2=receiver)) | (Q(user1=receiver) & Q(user2=sender))).first()
            text = serializer.validated_data.get("text")
            attachment_url = None
            attachment = serializer.validated_data.get("attachment")
            if attachment is not None:
                img_array = base64.b64decode(attachment)
                image = Image(image=img_array)
                image.save()
                attachment_url = f"/images/{image.pk}"
            message = Message(conversation=conversation, sender=sender, text=text, attachment_url=attachment_url)
            message.save()
            return Response({'status': {'successful': True, 'message': "Message is successfully added"}})
    
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
