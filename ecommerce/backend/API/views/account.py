from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser
from rest_framework.renderers import JSONRenderer

from ..utils import permissions, Role
from ..utils.jwttoken import generate_access_token
from ..utils.crypto import Crypto
from ..models import User, Customer
from ..serializers import account_serializer

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


#Testing purposes, not actual implementation
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def register(request):
    crypto = Crypto()
    username = request.data["username"]
    salt = crypto.getSalt()
    password_hash = crypto.getHashedPassword(request.data["password"], salt)
    existing_user = User.objects.filter(username=username).first()
    if existing_user is not None:
        return Response("Username is already in use")
    user = User(username=request.data["username"], email=request.data["email"], password_salt=salt, password_hash=password_hash, role = Role.CUSTOMER.value)
    user.save()
    customer = Customer(first_name=request.data["firstname"], last_name=request.data["lastname"], user=user)
    customer.save()
    return Response("Success")

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






