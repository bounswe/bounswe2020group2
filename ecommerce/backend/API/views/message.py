from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from django.db.models import Q, Case, When
import base64

from ..utils import permissions, Role
from ..models import User, Conversation, Message, Image
from ..serializers.message_serializer import *

# serves GET, POST requests for /messages endpoint
@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_messages(request):
    sender = request.user
    # if user is not logged in, return 403_FORBIDDEN
    if not sender.id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # get all conversations
    if request.method == 'GET':
        # get all conversations
        conversations = Conversation.objects.filter(Q(user1=sender) | Q(user2=sender))
        # sort conversations with respect to the last message
        conversation_finaltime_dict = {conv.id: Message.objects.filter(conversation=conv).latest('date').id for conv in conversations}
        conv_ids_latest_first = list(reversed(sorted(conversation_finaltime_dict, key=conversation_finaltime_dict.get)))
        # source: https://stackoverflow.com/questions/4916851/django-get-a-queryset-from-array-of-ids-in-specific-order 
        preserved = Case(*[When(pk=pk, then=pos) for pos, pk in enumerate(conv_ids_latest_first)])
        conversations = Conversation.objects.filter(pk__in=conv_ids_latest_first).order_by(preserved)
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
            # save the attachment as image if exists
            attachment_url = None
            attachment = serializer.validated_data.get("attachment")
            if attachment is not None:
                img_array = base64.b64decode(attachment)
                image = Image(image=img_array)
                image.save()
                attachment_url = f"/images/{image.pk}"
            # save message
            message = Message(conversation=conversation, sender=sender, text=text, attachment_url=attachment_url)
            message.save()
            return Response({'status': {'successful': True, 'message': "Message is successfully added"}})
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
