import datetime
from django.conf import settings
import jwt

def generate_access_token(user_id):
    token_payload = {
        'id': user_id,
        'exp': datetime.datetime.now() + datetime.timedelta(days=100),
        'iat': datetime.datetime.now()
    }
    access_token = jwt.encode(token_payload, settings.SECRET_KEY, algorithm='HS256').decode('utf-8')
    return access_token

def generate_mail_token(user_id):
    token_payload = {
        'id': user_id,
        'exp': datetime.datetime.now() + datetime.timedelta(days=2),
        'iat': datetime.datetime.now()
    }
    access_token = jwt.encode(token_payload, settings.SECRET_KEY, algorithm='HS256').decode('utf-8')
    return access_token