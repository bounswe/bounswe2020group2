from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers import shopping_cart_serializer, shopping_cart_item_serializer

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous])
def list_shopping_cart(request, id): #userId
    # if user not found
    if User.objects.filter(id=id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    sc_items = ShoppingCartItem.objects.filter(customer_id=id).values('id', 'product_id', 'amount')
    sci_serializer = shopping_cart_item_serializer.ShoppingCartItemSerializer(sc_items, many=True)
    return Response(sci_serializer.data)


@api_view(['POST'])
@permission_classes([permissions.AllowAnonymous])
def add_shopping_cart_item(request, id):
    # if request.user.pk != id:
    #     return Response(status=status.HTTP_403_FORBIDDEN)

    serializer = shopping_cart_serializer.ShoppingCartRequestSerializer(data=request.data)
    if not serializer.is_valid():
        context = { 'succesful': False,
                    'message': "Invalid input"
        }
        return Response(context)

    user = User.objects.get(pk=int(id))
    if user is None:
        context = { 'succesful': False,
                    'message': "Invalid user"
        }
        return Response(context)
    amount = serializer.validated_data.get("amount")
    product_id = serializer.validated_data.get("productId")
    product = Product.objects.get(pk=int(product_id))
    if product is None:
        context = { 'succesful': False,
                    'message': "Invalid products"
        }
        return Response(context)

    stock_amount = product.stock_amount
    shopping_cart = ShoppingCartItem.objects.filter(customer_id=user.pk).filter(product_id=product.pk).first()

    if shopping_cart is None:
        shopping_cart = ShoppingCartItem(customer=user, product=product, amount=amount)
    else:
        shopping_cart.amount+=amount
        
    
    if shopping_cart.amount > stock_amount:
        context = { 'succesful': False,
                    'message': "Stock Amount is reached"
        }
        return Response(context)
    elif shopping_cart.amount < 0:
        context = { 'succesful': False,
                    'message': "Amount cannot be negative"
        }
        return Response(context)
    shopping_cart.save()

    context = { 'succesful': True,
                'message': "Product is added to the cart succesfully."
    }
    return Response(context)
    