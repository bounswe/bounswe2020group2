from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory
from ..serializers.product_serializer import ProductSerializer







@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def get_categories(request):

    categories = Category.objects.all()

    response_categories = []

    for category in categories:
        c_name = category.name
        c_id = category.id

        subcategories = Subcategory.objects.filter(category_id=c_id).all()

        response_subcategories = []

        for subcategory in subcategories:
            sc_name = subcategory.name
            sc_id = subcategory.id

            response_subcategories.append({'name':sc_name,'id':sc_id})

        
        response_categories.append({'name':c_name,'id':c_id,'subcategories':response_subcategories})




    return Response({'categories':response_categories})