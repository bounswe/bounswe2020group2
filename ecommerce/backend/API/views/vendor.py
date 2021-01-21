from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Vendor,ImageUrls

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous]) 
def vendor_details(request,vendor_id):
    #print(vendor_id)
    vendor = Vendor.objects.filter(id=vendor_id).first()
    if vendor is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    return Response({'title':vendor.title,'image_url':vendor.image_url,'first_name':vendor.first_name,'last_name':vendor.last_name,'last_name':vendor.last_name,'total_rating':vendor.total_rating,'rating_count':vendor.rating_count,'description':vendor.description})
