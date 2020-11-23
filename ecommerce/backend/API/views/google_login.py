from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.parsers import JSONParser

from API.utils import permissions, Role
from API.utils.jwttoken import generate_access_token
from API.utils.crypto import Crypto
from API.models import User
from API.serializers import google_token_serializer

from google.oauth2 import id_token
from google.auth.transport import requests
from google.auth.transport.requests import AuthorizedSession
import google.oauth2.credentials
# Create your views here.
@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def googlelogin(request):
    
    data = JSONParser().parse(request)
    serializer = google_token_serializer.GoogleTokenSerializer(data=data)
    print(serializer)
    if not serializer.is_valid():
        return Response("Invalid input from frontend.")
    crypto = Crypto()
    token = serializer.validated_data.get("id_token")
    idinfo = id_token.verify_oauth2_token(token, requests.Request(), "780650655620-8qi5er6094ouirlb66b2c0hm6hlfo9s8.apps.googleusercontent.com")
    creds = google.oauth2.credentials.Credentials(token)
    authed_session = AuthorizedSession(creds)
    response = authed_session.get('https://openidconnect.googleapis.com/v1/userinfo')
    user = User.objects.filter(email=response["email"]).first()
    print(response);
    if user is None:
        user_serializer = account_serializer.LoginResponseSerializer(
            user,
            context = { 'is_successful': True,
                        'message': "Giriş başarılı"
            }
        )
        return Response(user_serializer.data)
    else:
       user_serializer = account_serializer.LoginResponseSerializer(
            User(role=-1),
            context = { 'is_successful': False,
                        'message': "Kullanıcı adı ya da şifre yanlış"
            }
        )
       return Response(user_serializer.data)