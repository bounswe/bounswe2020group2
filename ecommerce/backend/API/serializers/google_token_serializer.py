from rest_framework import serializers

class GoogleTokenSerializer(serializers.Serializer):
    id_token = serializers.CharField(max_length=32)
class GoogleResponseSerializer(serializers.Serializer):
    id_token = serializers.CharField(max_length=32)