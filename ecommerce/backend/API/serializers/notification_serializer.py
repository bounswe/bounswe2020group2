from rest_framework import serializers
from ..models import Notification
import json

class NotificationSerializer(serializers.ModelSerializer):
    
    type = serializers.SerializerMethodField('get_type')
    argument = serializers.SerializerMethodField('get_argument')

    def get_type(self, obj):
        if obj.notification_type == 1:
            return 'order_status_change'
        if obj.notification_type == 2:
            return 'price_change' 
        return ''
    
    def get_argument(self, obj):
        return json.loads(obj.argument)

    class Meta: 
        model = Notification
        fields  = ('id', 'type', 'is_seen', 'date', 'argument')