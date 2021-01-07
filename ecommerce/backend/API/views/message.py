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
    sender_user_id = request.user.pk
    user = User.objects.get(sender_user_id)
    # get all message
    if request.method == 'GET':
        # get all non-deleted cards of the customer
        cards = Card.objects.filter(user_id=customer_id).filter(is_deleted=False)
        # serialize them into a json array
        card_serializer = CardResponseSerializer(cards, many=True)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'cards': card_serializer.data})
    # add message
    elif request.method == 'POST':
        serializer = MessageRequestSerializer(data=request.data)
        # check if the formatted data is valid
        if serializer.is_valid():
            # create a Card object from the formatted data, and save it to the database
            name = serializer.validated_data.get("name")
            owner_name = serializer.validated_data.get("owner_name")
            serial_number = serializer.validated_data.get("serial_number")
            expiration_date = serializer.validated_data.get("expiration_date")
            expiration_month = expiration_date.get("month")
            expiration_year = expiration_date.get("year")
            cvv = serializer.validated_data.get("cvv")
            card = Card(user=user, name=name, owner_name=owner_name, serial_number=serial_number, expiration_month=expiration_month, expiration_year=expiration_year, cvv=cvv)
            card.save()
            return Response({'card_id': card.id, 'status': {'successful': True, 'message': "Card is successfully added"}})
    
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
