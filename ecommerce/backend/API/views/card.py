from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Card
from ..serializers.card_serializer import *

@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_specific_card(request, customer_id, card_id):
    # return 403_FORBIDDEN if it is trying to reach others content
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # return 400_BAD_REQUEST if no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    user = User.objects.get(pk=int(customer_id))
    #get single card
    if request.method == 'GET':
        # get the specific card if it is not soft deleted
        card = Card.objects.filter(user_id=customer_id).filter(id=card_id).filter(is_deleted=False).first()
        if card is None:
            return Response({'status': {'successful': False, 'message': "No such card is found"}})
        # serialize it as a single json object
        card_serializer = CardResponseSerializer(card)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'card': card_serializer.data})
    #delete single card 
    elif request.method == 'DELETE':
        try:
            # check if such card exists and not soft deleted
            card = Card.objects.filter(user_id=customer_id).filter(id=card_id).filter(is_deleted=False).first()
            if card is not None:
                # if exists, then soft delete that card since cards may be referenced by another table
                card.is_deleted = True
                card.save()
                return Response({'status': {'successful': True, 'message': "Successfully deleted"}})
            else:
                return Response({'status': {'successful': False, 'message': "No such card is found"}})
        except Exception as e:
            return Response({'status': {'successful': False, 'message': str(e)}})
    #update a card
    elif request.method == 'PUT':
        # check if such card exists and not soft deleted
        card = Card.objects.filter(user_id=customer_id).filter(id=card_id).filter(is_deleted=False).first()
        if card is None:
            return Response({'status': {'successful': False, 'message': "No such card is found"}})
        # update the fields of the Address object if exists
        card.name = request.data.get("name")
        card.owner_name = request.data.get("owner_name")
        card.serial_number = request.data.get("serial_number")
        expiration_date = request.data.get("expiration_date")
        card.expiration_month = expiration_date.get("month")
        card.expiration_year = expiration_date.get("year")
        card.cvv = request.data.get("cvv")
        card.save()
        return Response({'status': {'successful': True, 'message': "Card is successfully updated"}})
    return Response({'status': {'successful': False, 'message': "Error occurred"}})

@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_cards(request, customer_id):
    # return 403_FORBIDDEN if it is trying to reach others content
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # return 400_BAD_REQUEST if no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.get(pk=int(customer_id))
    # get all cards
    if request.method == 'GET':
        # get all non-deleted cards of the customer
        cards = Card.objects.filter(user_id=customer_id).filter(is_deleted=False)
        # serialize them into a json array
        card_serializer = CardResponseSerializer(cards, many=True)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'cards': card_serializer.data})
    # add card
    elif request.method == 'POST':
        serializer = CardRequestSerializer(data=request.data)
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
