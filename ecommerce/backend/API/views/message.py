from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from django.db.models import Q
import base64

from ..utils import permissions
from ..models import User, Conversation, Message, Image
from ..serializers.message_serializer import *

# serves POST requests for /messages endpoint
@api_view(['POST'])
@permission_classes([permissions.IsAuthenticated])
def manage_messages(request):
    sender = request.user
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
        # save the attachment as image if exists
        attachment_url = None
        attachment = serializer.validated_data.get("attachment")
        if attachment is not None:
            img_array = base64.b64decode(attachment)
            image = Image(image=img_array)
            image.save()
            attachment_url = f"/image/{image.pk}"
        # save message
        message = Message(conversation=conversation, sender=sender, text=text, attachment_url=attachment_url)
        message.save()
        return Response({'status': {'successful': True, 'message': "Message is successfully added"}})
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
