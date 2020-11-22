from rest_framework import serializers

class ShoppingCartRequestSerializer(serializers.Serializer):
    productId = serializers.IntegerField()
    amount = serializers.IntegerField()