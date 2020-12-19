from API.models.user import Address
from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Address
from ..serializers.address_serializer import *

@api_view(['GET', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_single_address(request, customer_id, address_id):
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    #get single address
    if request.method == 'GET':
        address = Address.objects.filter(user_id=customer_id).filter(id=address_id)
        if address is None:
            return Response({'successful': False, 'message': "No such address is found"})
        address_serializer = AddressResponseSerializer(address, many=True)
        return Response(address_serializer.data)
    #delete single address 
    elif request.method == 'DELETE':
        try:
            address = Address.objects.filter(user_id=customer_id).filter(id=address_id)
            if address is not None:
                address.delete()
                return Response({'successful': True, 'message': "Successfully deleted"})
            else:
                return Response({'successful': True, 'message': "No such address is found"})
        except Exception as e:
            return Response({'successful': False, 'message': str(e)})
    return Response({'successful': False, 'message': "Error occurred"})

@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_multiple_addresses(request, customer_id):
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.get(pk=int(customer_id))
    # get all adresses
    if request.method == 'GET':
        addresses = Address.objects.filter(user_id=customer_id)
        address_serializer = AddressResponseSerializer(addresses, many=True)
        return Response(address_serializer.data)
    # add address
    elif request.method == 'POST':
        serializer = AddressRequestSerializer(data=request.data)
        if serializer.is_valid():
            title = serializer.validated_data.get("title")
            address = serializer.validated_data.get("address")
            province = serializer.validated_data.get("province")
            city = serializer.validated_data.get("city")
            country = serializer.validated_data.get("country")
            phone = serializer.validated_data.get("phone")
            phone_country_code = phone.get("country_code")
            phone_number = phone.get("number")
            zip_code = serializer.validated_data.get("zip_code")
            address = Address(user=user, title=title, address=address, province=province, city=city, 
                country=country, phone_country_code=phone_country_code, phone_number=phone_number, zip_code=zip_code)
            address.save()
            return Response({'successful': True, 'message': "Address is successfully added"})
    return Response({'successful': False, 'message': "Error occurred"})
