from rest_framework.decorators import api_view
from rest_framework.response import Response

from .serializers import UserSerializer

from .models import User

# Create your views here.


@api_view(['GET'])
def apiOverview(request):

    users = User.objects.all()

    user_serializer = UserSerializer(users, many=True)

    return Response(user_serializer.data)
