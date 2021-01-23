from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from ..utils import permissions, Role

from ..serializers.notification_serializer import NotificationSerializer
from ..models import User, Notification
from rest_framework import status

@api_view(['GET', 'POST'])
@permission_classes([permissions.IsCustomerUser])
def manage_notifications(request):

    user = request.user

    if request.method == 'GET':
        notifications = Notification.objects.filter(user=user).all()
        notification_serializer = NotificationSerializer(notifications, many=True)
        return Response(notification_serializer.data)




@api_view(['POST'])
@permission_classes([permissions.IsAuthenticated])
def all_notifications_seen(request):
    user = request.user

    notifications = Notification.objects.filter(user=user).all()
    for notification in notifications:
        notification.is_seen = True
        notification.save()

    return Response({'status': {'successful': True, 'message':'All notifications of user made seen successfully.'}})

@api_view(['POST'])
@permission_classes([permissions.IsAuthenticated])
def single_notification_seen(request, notification_id):
    user = request.user

    notification = Notification.objects.filter(pk=notification_id).first()
    if notification is None:
        return Response(status=status.HTTP_404_NOT_FOUND)
    
    if notification.user != user:
        return Response(status=status.HTTP_403_FORBIDDEN)

    notification.is_seen = True
    notification.save()

    return Response({'status': {'successful': True, 'message':'Notification made seen successfully.'}})