from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes

from API.utils import permissions, Role
from API.models.product import Product
from API.model_serializers import ProductSerializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def homepage_products(request,no):

    products = Product.objects.all()[:no]
    serializer = ProductSerializer(products, many=True)

    return Response(serializer.data)