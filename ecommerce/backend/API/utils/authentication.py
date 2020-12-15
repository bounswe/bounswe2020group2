import jwt
from rest_framework.authentication import BaseAuthentication
from rest_framework import exceptions
from django.conf import settings
from API.models import User

class JWTAuthentication(BaseAuthentication):

    def authenticate(self, request):
        auth_header = request.headers.get('Authorization')
        if not auth_header:
            user = User(role=0)
            return (user, None)
        try:
            access_token = auth_header.split(' ')[1]
            payload = jwt.decode(
                access_token, settings.SECRET_KEY, algorithms=['HS256'])

        except jwt.InvalidSignatureError:
            raise exceptions.AuthenticationFailed('invalid signature')
        except jwt.ExpiredSignatureError:
            raise exceptions.AuthenticationFailed('token expired')
        except IndexError:
            raise exceptions.AuthenticationFailed("invalid format")

        user = User.objects.filter(pk=payload['id']).first()
        if user is None:
            raise exceptions.AuthenticationFailed('user not found')
        
        return(user, None)

    def authenticate_mail(self, uidb64):
        message = ""
        control = False
        user = None
        try:
            payload = jwt.decode(
                uidb64, settings.SECRET_KEY, algorithms=['HS256'])

        except jwt.InvalidSignatureError:
            message= 'Invalid'
            control = True
        except jwt.ExpiredSignatureError:
            message= 'Expired'
            control = True
        except IndexError:
            message= 'Invalid'
            control = True

        if(not control):
            user = User.objects.filter(pk=payload['id']).first()
            if user is None:
                message= 'Invalid'
            elif user.is_verified is True:
                message= 'Verified'
            else:
                message = "Success"

        return(user, message)