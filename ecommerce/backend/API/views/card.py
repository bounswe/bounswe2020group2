from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Card
from ..serializers.card_serializer import *

@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_single_card(request, customer_id, card_id):
    # reaching others' content is forbidden
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)

    user = User.objects.get(pk=int(customer_id))
    #get single card
    if request.method == 'GET':
        card = Card.objects.filter(user_id=customer_id).filter(id=card_id).first()
        if card is None or card.is_deleted:
            return Response({'successful': False, 'message': "No such card is found"})
        card_serializer = CardResponseSerializer(card)
        return Response(card_serializer.data)
    #delete single card 
    elif request.method == 'DELETE':
        try:
            card = Card.objects.filter(user_id=customer_id).filter(id=card_id)
            if card is not None or not card.is_deleted:
                card.is_deleted = True # soft delete
                card.save()
                return Response({'successful': True, 'message': "Successfully deleted"})
            else:
                return Response({'successful': True, 'message': "No such card is found"})
        except Exception as e:
            return Response({'successful': False, 'message': str(e)})
    #update a card
    elif request.method == 'PUT':
        card = Card.objects.filter(user_id=customer_id).filter(id=card_id).first()
        if card is None or card.is_deleted:
            return Response({'successful': False, 'message': "No such card is found"})
        card.name = request.data.get("name")
        card.owner_name = request.data.get("owner_name")
        card.serial_number = request.data.get("serial_number")
        expiration_date = request.data.get("expiration_date")
        card.expiration_month = expiration_date.get("month")
        card.expiration_year = expiration_date.get("year")
        card.cvv = request.data.get("cvv")
        card.save()
        return Response({'successful': True, 'message': "Card is successfully updated"})
    return Response({'successful': False, 'message': "Error occurred"})

@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_multiple_cards(request, customer_id):
    # reaching others' content is forbidden
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.get(pk=int(customer_id))
    # get all adresses
    if request.method == 'GET':
        cards = Card.objects.filter(user_id=customer_id)
        card_serializer = CardResponseSerializer(cards, many=True)
        return Response(card_serializer.data)
    # add address
    elif request.method == 'POST':
        serializer = CardRequestSerializer(data=request.data)
        if serializer.is_valid():
            name = serializer.validated_data.get("name")
            owner_name = serializer.validated_data.get("owner_name")
            serial_number = serializer.validated_data.get("serial_number")
            expiration_date = serializer.validated_data.get("expiration_date")
            expiration_month = expiration_date.get("month")
            expiration_year = expiration_date.get("year")
            cvv = serializer.validated_data.get("cvv")
            card = Card(user=user, name=name, owner_name=owner_name, serial_number=serial_number, expiration_month=expiration_month, expiration_year=expiration_year, cvv=cvv)
            card.save()
            return Response({'successful': True, 'message': "Card is successfully added"})
    
    return Response({'successful': False, 'message': "Error occurred"})
