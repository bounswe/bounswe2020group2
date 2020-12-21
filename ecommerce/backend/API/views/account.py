from django.contrib.sites.shortcuts import get_current_site

from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser
from rest_framework.renderers import JSONRenderer

from API.utils import permissions, Role
from API.utils.jwttoken import generate_access_token, generate_mail_token
from API.utils.validators import validate_register_request
from API.utils.crypto import Crypto
from ..utils import verify_email
from API.models import User, Customer, Address, Vendor
from API.serializers import account_serializer, address_serializer

# Create your views here.


@api_view(['GET'])
@permission_classes([permissions.IsAuthenticated])
def init(request):
    user_serializer = account_serializer.UserSerializer(request.user)
    return Response(user_serializer.data)

@api_view(['GET'])
@permission_classes([permissions.IsCustomerUser])
def apiOverview(request):
    print(request.user.pk)
    return Response("Here test permission function")


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def register(request):
    validation_result = validate_register_request(request)
    if validation_result[0] is False:
        context = {
            'successful': False,
            'message': validation_result[1]
        }
        return Response(context)
    
    crypto = Crypto()
    username = request.data["username"]
    salt = crypto.getSalt()
    password_hash = crypto.getHashedPassword(request.data["password"], salt)
    existing_user = User.objects.filter(username=username).first()
    if existing_user is not None:
        context = {
            'successful': False,
            'message': 'Username is already in use'
        }
        return Response(context)
    
    user = User(username=request.data["username"], email=request.data["email"], password_salt=salt, password_hash=password_hash, role = Role.CUSTOMER.value)
    user.save()
    customer = Customer(first_name=request.data["firstname"], last_name=request.data["lastname"], user=user)
    customer.save()
    context = {
            'successful': True,
            'message': 'Signup succeeded'
    }

    to_email = request.data["email"]
    current_site = get_current_site(request)
    verify_message = verify_email.email_send_verify(to_email=to_email, current_site=current_site, user=user)

    context = {
            'successful': True,
            'message': 'Signup succeeded, ' + verify_message
    }
    return Response(context)


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def vendor_register(request):
    validation_result = validate_register_request(request)
    serializer = address_serializer.AddressRequestSerializer(data=request.data["address"])
    if validation_result[0] is False:
        context = {
            'successful': False,
            'message': validation_result[1]
        }
        return Response(context)
    
    if not serializer.is_valid():
        context = {
            'successful': False,
            'message': "Invalid Address"
        }
        return Response(context)
    
    crypto = Crypto()
    username = request.data["username"]
    salt = crypto.getSalt()
    password_hash = crypto.getHashedPassword(request.data["password"], salt)
    existing_user = User.objects.filter(username=username).first()
    if existing_user is not None:
        context = {
            'successful': False,
            'message': 'Username is already in use'
        }
        return Response(context)
    user = User(username=request.data["username"], email=request.data["email"], password_salt=salt, password_hash=password_hash, role = Role.VENDOR.value)
    user.save()
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
    vendor = Vendor(first_name=request.data["firstname"], last_name=request.data["lastname"], user=user)
    address = Address(user=user, title=title, address=address, province=province, city=city, name=name, surname=surname, 
        country=country, phone_country_code=phone_country_code, phone_number=phone_number, zip_code=zip_code)
    vendor.save()
    address.save()
    context = {
            'successful': True,
            'message': 'Signup succeeded'
        }
    return Response(context)

@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def login(request):
    data = JSONParser().parse(request)
    serializer = account_serializer.LoginRequestSerializer(data=data)
    if not serializer.is_valid():
        return Response("Invalid input")
    crypto = Crypto()
    username = serializer.validated_data.get("username")
    password = serializer.validated_data.get("password")
    user = User.objects.filter(username=username).first()
    if user is None:
        user_serializer = account_serializer.LoginResponseSerializer(
            User(role=-1),
            context = { 'is_successful': False,
                        'message': "Kullanıcı adı ya da şifre yanlış"
            }
        )
        return Response(user_serializer.data)
    computed_hash = crypto.getHashedPassword(password, user.password_salt)
    if computed_hash == user.password_hash:
        user_serializer = account_serializer.LoginResponseSerializer(
            user,
            context = { 'is_successful': True,
                        'message': "Giriş başarılı"
            }
        )
        return Response(user_serializer.data)
    else :
        user_serializer = account_serializer.LoginResponseSerializer(
            User(role=-1),
            context = { 'is_successful': False,
                        'message': "Kullanıcı adı ya da şifre yanlış"
            }
        )
        return Response(user_serializer.data)

