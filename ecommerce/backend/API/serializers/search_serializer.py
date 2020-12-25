from rest_framework import serializers
from ..utils import Role

from ..models import product as product_model
from ..models import interactions as interactions_model

class SearchProductsSerializer(serializers.Serializer):
    query = serializers.CharField(max_length=2000)


