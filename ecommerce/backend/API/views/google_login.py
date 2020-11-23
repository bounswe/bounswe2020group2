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
    #token = serializer.validated_data.get("id_token")
    token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRlZGMwMTJkMDdmNTJhZWRmZDVmOTc3ODRlMWJjYmUyM2MxOTcyNGQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNzgwNjUwNjU1NjIwLThxaTVlcjYwOTRvdWlybGI2NmIyYzBobTZobGZvOXM4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNzgwNjUwNjU1NjIwLThxaTVlcjYwOTRvdWlybGI2NmIyYzBobTZobGZvOXM4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA5MjEwNTQ2NjY5NDExNDA2Njg0IiwiZW1haWwiOiJzYWZmYXIubWVoZGkzQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiNUlJcjJoclpva1piOFpxQ1lzUGFNZyIsIm5hbWUiOiJNZWhkaSBTYWZmYXIiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FPaDE0R2p4MjlKc21ZTUpfeV9CR0lWSEJJVGRvRVBjVHJHdmxRWnhQN3BfQnBzPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik1laGRpIiwiZmFtaWx5X25hbWUiOiJTYWZmYXIiLCJsb2NhbGUiOiJlbiIsImlhdCI6MTYwNjEzOTEzMSwiZXhwIjoxNjA2MTQyNzMxLCJqdGkiOiI3MDJmNjcxZTYzMjI0NWFiNTgzMGYyYmMyZDdhNGI2NzAxYjAzZjMxIn0.kmQPtIU6zHgZ_yQH38MPyvO0ZgYzbXmDrvmPSEpCpaZLEm6OEKWNM8IQUp3LjPdu3t-jP9ftgtvR6ijUBn2XWFYIEWqLNeZ37z2n3K2G0OAtRtu1hLBH5tQSs_kO3om6y2wQtZHpV81fMH1vpce6Lyh0Hqk_K56NtnfD8q5hzyjsI-rQGhNcPTOxTwRaqIEj79vDcgY75Zi37SwSlfWvYYk7YXqug4HPBaMPwb6IRny0o5frqZPE7neX3bv-yld_Yd6e_Furm9xB12enI93_ihpJUxX2tQtxelLFh5pHkEEy0EOBQMfo5Hq_ZBGfcXY3WTr8vjWxmE1Be-dPcOilIg"
    idinfo = id_token.verify_oauth2_token(token, requests.Request(), "780650655620-8qi5er6094ouirlb66b2c0hm6hlfo9s8.apps.googleusercontent.com")
    creds = google.oauth2.credentials.Credentials(token)
    authed_session = AuthorizedSession(creds)
    response = authed_session.get('https://openidconnect.googleapis.com/v1/userinfo')
    user = User.objects.filter(email=response["email"]).first()
    print(response);
    if computed_hash == user.password_hash:
        resp_serializer = google_token_serializer.LoginResponseSerializer(
            User(role=-1),
            context = { 'is_successful': False,
                        'message': "Kullanıcı adı ya da şifre yanlış"
            }
        )
        return Response(user_serializer.data)
    else :
        return Response("Invalid Credentials")