from django.core.mail import message
from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from django.contrib.sites.shortcuts import get_current_site
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser
from rest_framework.decorators import api_view, renderer_classes
from rest_framework.renderers import JSONRenderer, TemplateHTMLRenderer

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers import checkout_product_serializer, checkout_shopping_cart_serializer
from ..utils import authentication, verify_email
from django.http import JsonResponse


'''
This function control 'user/verify' endpoint and sends email to user to verify account.
request: includes information about user, HTTP_REFERER, method
@return
Response(POST): includes successfull serializer(successfull value and message)
'''
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def user_verify(request):
    jwt = authentication.JWTAuthentication()
    user = jwt.authenticate(request=request)[0]
    to_email = user.email
    current_site = request.META.get('HTTP_REFERER')
    verify_message = verify_email.email_send_verify(to_email=to_email, current_site=current_site, user=user)

    return Response({'status': {'successful': True, 
            'message': verify_message}})


'''
This function control 'email-verify/<uidb64>' endpoint and verify email if token is not expired and true.
@param
request: includes information about user, method
uidb64:  token which is expired in 2 daysş
@return
Response: includes successfull serializer(successfull value and message)
'''
@renderer_classes((TemplateHTMLRenderer, JSONRenderer))
def email_verify(request, uidb64):
    jwt = authentication.JWTAuthentication()
    user, message = jwt.authenticate_mail(uidb64=uidb64)
    successful = False
    if message == 'Success':
        user.is_verified = True
        user.save()
        successful = True
    else:
        successful = False

    return JsonResponse({'status': {'successful': successful, 
            'message': message}})
