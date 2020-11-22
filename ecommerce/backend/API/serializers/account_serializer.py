from rest_framework import serializers
from ..models import user as user_model
from ..utils.jwttoken import generate_access_token
from ..utils import Role

from ..models import product as product_model
from ..models import interactions as interactions_model

class LoginRequestSerializer(serializers.Serializer):
    username = serializers.CharField(max_length=32)
    password = serializers.CharField(max_length=32)


class UserSerializer(serializers.ModelSerializer):
    token = serializers.SerializerMethodField()
    firstname = serializers.SerializerMethodField()
    lastname = serializers.SerializerMethodField()

    def get_firstname(self, obj):
        if obj.role == Role.CUSTOMER.value:
            customer = user_model.Customer.objects.filter(user=obj).first()
            return customer.first_name
        else:
            return ""

    def get_lastname(self, obj):
        print(obj.role)
        if obj.role == Role.CUSTOMER.value:
            customer = user_model.Customer.objects.filter(user=obj).first()
            return customer.last_name
        else:
            return ""

    def get_token(self, obj):
        if obj.pk is not None:
            return generate_access_token(obj.pk)
        else:
            return ""
    
    class Meta:
        model = user_model.User
        fields = ('id', 'email', 'token', 'firstname', 'lastname')
    

class LoginResponseSerializer(serializers.ModelSerializer):
    user = serializers.SerializerMethodField('get_user')
    status = serializers.SerializerMethodField('get_status')

    class Meta:
        model = user_model.User
        fields = ('user', 'status')

    def get_user(self, obj):
        return UserSerializer(obj).data

    def get_status(self, obj):
        is_successful = self.context.get("is_successful")
        message = self.context.get("message")
        return {
            'success': is_successful,
            'message': message
        }




'''class ProductSerializer(serializers.ModelSerializer):

    class Meta:
        model = product_model.Product


class ShoppingCartResponse(serializers.ModelSerializer):
    product = serializers.SerializerMethodField('get_product')

    class Meta:
        model = product_model.ShoppingCartItem
        fields = ('id', 'amount', 'product')

    def get_product(self, obj):
        product = product_model.Product.objects.get(pk=obj.product_id)
        return ProductSerializer(product).data'''



