from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json
import base64
from ..utils import permissions, Role
from ..models import Vendor,ImageUrls,Image,User

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous]) 
def vendor_details(request,vendor_id):
    vendor = Vendor.objects.filter(user_id=vendor_id).first()
    if vendor is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    return Response({'title':vendor.title,'image_url':vendor.image_url,'first_name':vendor.first_name,'last_name':vendor.last_name,'last_name':vendor.last_name,'total_rating':vendor.total_rating,'rating_count':vendor.rating_count,'description':vendor.description})

@api_view(['PUT'])
@permission_classes([permissions.IsVendorUser]) 
def vendor_profile(request):
    vendor = User.objects.filter(id=request.user.id).first()
    if "image" not in request.data:
        Vendor.objects.filter(user_id=vendor.id).update(title=request.data["title"],description=request.data["description"])
    else:
        img_array = base64.b64decode(request.data["image"])
        image = Image(image=img_array)
        image.save()
        image_url="/image/"+str(image.pk)
        Vendor.objects.filter(user_id=vendor.id).update(title=request.data["title"],description=request.data["description"],image_url=image_url)
    return Response({
          "status": {
              "successful": "true",
              "message": "Profile updated successfully"
          }
      }
)
