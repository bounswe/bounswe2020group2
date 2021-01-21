from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from ..utils import permissions


# serves POST requests for /messages endpoint
@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_messages(request):
    # add messages
    return Response({'status': {'successful': False, 'message': "Error occurred"}})
