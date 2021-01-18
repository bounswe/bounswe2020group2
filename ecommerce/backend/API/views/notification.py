from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from ..utils import permissions, Role

from ..serializers.notification_serializer import NotificationSerializer
from ..models import User, Notification


@api_view(['GET', 'POST'])
@permission_classes([permissions.IsCustomerUser])
def manage_notifications(request):

    user = request.user

    if request.method == 'GET':
        notifications = Notification.objects.filter(user=user).all()
        notification_serializer = NotificationSerializer(notifications, many=True)
        return Response(notification_serializer.data)