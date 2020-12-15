from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from django.contrib.sites.shortcuts import get_current_site
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers import checkout_product_serializer, checkout_shopping_cart_serializer
from ..utils import authentication, verify_email


@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def user_verify(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]
    to_email = user.email
    current_site = get_current_site(request)
    verify_message = verify_email.email_send_verify(to_email=to_email, current_site=current_site, user=user)

    context = { 'succesful': True,
                'message': verify_message
    }
    return Response(context)


@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def email_verify(request, uidb64):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate_mail(uidb64=uidb64)
    user.is_verified = True
    user.save()
    context = { 'succesful': True,
            }

    return Response(context)