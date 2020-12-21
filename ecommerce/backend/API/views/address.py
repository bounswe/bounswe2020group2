from API.models.user import Address
from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status

from ..utils import permissions, Role
from ..models import User, Address
from ..serializers.address_serializer import *

@api_view(['GET','PUT', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_specific_address(request, customer_id, address_id):
    # reaching others' content is forbidden
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    #get single address
    if request.method == 'GET':
        address = Address.objects.filter(user_id=customer_id).filter(id=address_id).filter(is_deleted=False).first()
        if address is None:
            return Response({'status': {'successful': False, 'message': "No such address is found"}})
        address_serializer = AddressResponseSerializer(address)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'address': address_serializer.data})
    #delete single address 
    elif request.method == 'DELETE':
        try:
            address = Address.objects.filter(user_id=customer_id).filter(id=address_id).filter(is_deleted=False).first()
            if address is not None:
                address.is_deleted = True
                address.save()
                return Response({'status': {'successful': True, 'message': "Successfully deleted"}})
            else:
                return Response({'status': {'successful': False, 'message': "No such address is found"}})
        except Exception as e:
            return Response({'status': {'successful': False, 'message': str(e)}})
    #update an address
    elif request.method == 'PUT':
        address = Address.objects.filter(user_id=customer_id).filter(id=address_id).filter(is_deleted=False).first()
        if address is None:
            return Response({'status': {'successful': False, 'message': "No such address is found"}})
        address.title = request.data.get("title")
        address.name = request.data.get("name")
        address.surname = request.data.get("surname")
        address.address = request.data.get("address")
        address.province = request.data.get("province")
        address.city = request.data.get("city")
        phone = request.data.get("phone")
        address.phone_number = phone.get("number")
        address.phone_country_code = phone.get("country_code")
        address.country = request.data.get("country")
        address.zip_code = request.data.get("zip_code")
        address.save()
        #status içine al
        return Response({'status': {'successful': True, 'message': "Address is successfully updated"}})
    return Response({'status': {'successful': False, 'message': "Error occurred"}})

@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_addresses(request, customer_id):
    # reaching others' content is forbidden
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.get(pk=int(customer_id))
    # get all adresses
    if request.method == 'GET':
        addresses = Address.objects.filter(user_id=customer_id).filter(is_deleted=False)
        address_serializer = AddressResponseSerializer(addresses, many=True)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'addresses': address_serializer.data})
    # add address
    elif request.method == 'POST':
        serializer = AddressRequestSerializer(data=request.data)
        if serializer.is_valid():
            title = serializer.validated_data.get("title")
            name = serializer.validated_data.get("name")
            surname = serializer.validated_data.get("surname")
            address = serializer.validated_data.get("address")
            province = serializer.validated_data.get("province")
            city = serializer.validated_data.get("city")
            country = serializer.validated_data.get("country")
            phone = serializer.validated_data.get("phone")
            phone_country_code = phone.get("country_code")
            phone_number = phone.get("number")
            zip_code = serializer.validated_data.get("zip_code")
            address = Address(user=user, name=name, surname=surname, title=title, address=address, province=province, city=city, 
                country=country, phone_country_code=phone_country_code, phone_number=phone_number, zip_code=zip_code)
            address.save()
            return Response({'address_id': address.id, 'status': {'successful': True, 'message': "Address is successfully added"}})
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
