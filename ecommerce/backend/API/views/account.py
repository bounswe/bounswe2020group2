from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser

from API.utils import permissions, Role
from API.utils.jwttoken import generate_access_token
from API.utils.crypto import Crypto
from API.models import User
from API.serializers import account_serializer

# Create your views here.


@api_view(['GET'])
@permission_classes([permissions.IsAdminUser])
def apiOverview(request):
    print(request.user.pk)
    return Response("Here admin function")

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
        return Response("Invalid credentials")
    computed_hash = crypto.getHashedPassword(password, user.password_salt)
    if computed_hash == user.password_hash:
        return Response(generate_access_token(user.pk))
    else :
        return Response("Invalid Credentials")