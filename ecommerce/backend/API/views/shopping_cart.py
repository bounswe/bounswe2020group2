from django.http.response import HttpResponseForbidden, HttpResponseNotAllowed
from rest_framework import exceptions
    
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from rest_framework.parsers import JSONParser

from ..utils import permissions, Role
from ..models import User, ShoppingCartItem, Product
from ..serializers.shopping_cart_serializer import *

# serves GET, PUT, DELETE requests for the given customer_id and sc_item_id
@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_specific_shopping_cart_item(request, customer_id, sc_item_id):
    # return 403_FORBIDDEN if it is trying to reach others content
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # return 400_BAD_REQUEST if no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.get(pk=customer_id)
    #return single shopping cart item
    if request.method == 'GET':
        # get the specific item
        sc_item = ShoppingCartItem.objects.filter(customer_id=customer_id).filter(id=sc_item_id).first()
        # serialize it as a single json object
        sci_serializer = ShoppingCartResponseSerializer(sc_item)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'sc_item': sci_serializer.data})
    #update a shopping cart item
    elif request.method == 'PUT':
        # get the specific item
        sc_item = ShoppingCartItem.objects.filter(customer_id=customer_id).filter(id=sc_item_id).first()
        if sc_item is None:
            return Response({'status': {'successful': False, 'message': "No such item is found"}})
        # new amount should be positive
        if request.data.get("amount") <= 0:
            return Response({'status': {'successful': False, 'message': "Amount should be a positive number"}})
        sc_item.amount = request.data.get("amount")
        sc_item.save()
        return Response({'status': {'successful': True, 'message': "Item is successfully updated"}})
    #delete a shopping cart item, not a soft delete since we do not need anywhere else
    elif request.method == 'DELETE':
        # get the specific item
        sc_item = ShoppingCartItem.objects.filter(customer_id=customer_id).filter(id=sc_item_id)
        if sc_item is None:
            return Response({'status': {'successful': False, 'message': "No such item is found"}})
        else:
            # hard delete the item, since it is not referenced by any other table
            sc_item.delete()
            return Response({'status': { 'successful': True, 'message': "Successfully deleted"}})

# serves GET, POST requests for the given customer_id
@api_view(['GET', 'POST'])
@permission_classes([permissions.AllowAnonymous])
def manage_shopping_cart_items(request, customer_id):
    # return 403_FORBIDDEN if it is trying to reach others content
    if request.user.pk != customer_id:
        return Response(status=status.HTTP_403_FORBIDDEN)
    # return 400_BAD_REQUEST if no such user exists
    if User.objects.filter(id=customer_id).first() is None:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    user = User.objects.get(pk=customer_id)
    
    #return all shopping cart items
    if request.method == 'GET':
        sc_items = ShoppingCartItem.objects.filter(customer_id=customer_id)
        sci_serializer = ShoppingCartResponseSerializer(sc_items, many=True)
        return Response({'status': {'successful': True, 
            'message': "Successfully retrieved"}, 'sc_items': sci_serializer.data})
    #add a shopping cart item
    elif request.method == 'POST':
        serializer = ShoppingCartRequestSerializer(data=request.data)
        # check the validity of the format
        if not serializer.is_valid():
            return Response({'status': {'successful': False, 'message': "Invalid input"}})
        amount = serializer.validated_data.get("amount")
        if amount <= 0:
            return Response({'status': {'successful': False, 'message': "Amount should be a positive number"}})
        product_id = serializer.validated_data.get("product_id")
        sc_item = ShoppingCartItem.objects.filter(customer_id=customer_id).filter(product_id=product_id).first()
        # add new amount over the old amount if the item already exists in the shopping art
        if sc_item is not None:
            sc_item.amount += amount
            sc_item.save()
            return Response({'sc_item_id': sc_item.id, 'status': {'successful': True, 'message': "Product is added to the cart succesfully."}})
        # add new item otherwise
        product = Product.objects.get(pk=product_id)
        sc_item = ShoppingCartItem(customer=user, product=product, amount=amount)
        sc_item.save()
        return Response({'sc_item_id': sc_item.id, 'status': {'successful': True, 'message': "Product is added to the cart succesfully."}})
