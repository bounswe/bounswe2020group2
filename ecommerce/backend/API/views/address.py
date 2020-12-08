from API.models.user import Address
from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Address, Customer, Product
from ..serializers.address_serializer import AddressSerializer

@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_single_address(request, customer_id, address_id):
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    #get single address
    if request.method == 'GET':
        address = Address.objects.filter(user_id=customer_id).filter(id=address_id)
        if address is None:
            return Response({'succesful': False, 'message': "No such address is found"})
        address_serializer = AddressSerializer(address, many=True)
        return Response(address_serializer.data)
    #update single address
    elif request.method == 'PUT': 
        address_serializer = AddressSerializer(data=request.data)
        if address_serializer.is_valid(): 
            address_serializer.save() 
            return Response({'succesful': True, 'message': "Successfully updated"})
        else:
            return Response({'succesful': False, 'message': "Wrong format: Data could not be serialized"})
    #delete single address 
    elif request.method == 'DELETE':
        try:
            address = Address.objects.filter(user_id=customer_id).filter(id=address_id)
            if address is not None:
                address.delete()
                return Response({'succesful': True, 'message': "Successfully deleted"})
            else:
                return Response({'succesful': True, 'message': "No such address is found"})
        except Exception as e:
            return Response({'succesful': False, 'message': str(e)})
    return Response({'succesful': False, 'message': "Error occurred"})

@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_multiple_addresses(request, customer_id):
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    # get all adresses
    if request.method == 'GET':
        addresses = Address.objects.filter(user_id=customer_id)
        address_serializer = AddressSerializer(addresses, many=True)
        return Response(address_serializer.data)
    # add address
    elif request.method == 'POST':
        address_serializer = AddressSerializer(data=request.data)
        address = Address(**address_serializer.validated_data)
        address.save()
        return Response(address_serializer.data)
    return Response({'succesful': False, 'message': "Error occurred"})