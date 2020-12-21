from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory,Purchase
from ..serializers.address_serializer import AddressResponseSerializer
from ..serializers.product_serializer import ProductResponseSerializer
from ..utils.order_status import OrderStatus


@api_view(['GET','PUT'])
@permission_classes([permissions.IsVendorUser]) 
def vendor_orders(request):

    if request.method == 'GET': 

        response_purchases = []

        vendor = Vendor.objects.filter(user=request.user).first() 

        my_purchases = Purchase.objects.filter(vendor_id=vendor.id) 

        for purchase in my_purchases:

            total_price = purchase.amount*purchase.unit_price

            serializer = AddressResponseSerializer(purchase.address)
            adress = serializer.data

            serializer = ProductResponseSerializer(purchase.product)
            product = serializer.data

            response = {'amount':purchase.amount,'unit_price':purchase.unit_price,'total_price':total_price,'status':purchase.status,
            'purchase_date':purchase.purchase_date,'adress':adress, 'product':product, 'id':purchase.id}

            response_purchases.append(response)

        return Response({'orders':response_purchases})


    if request.method == 'PUT':

        if request.data['orderId'] == None:
            return Response({'is_successful': False, 'message':'Please provide orderId '})

        if request.data['orderStatus'] == None:
            return Response({'is_successful': False, 'message':'Please provide orderStatus '})
        
        vendor = Vendor.objects.filter(user=request.user).first()

        purchase_to_change = Purchase.objects.filter(id=request.data['orderId']).first()

        if purchase_to_change.vendor_id != vendor.id:
            return Response({'is_successful': False, 'message':'This order does not belong to you'})

        if request.data['orderStatus'] == 'cancelled':
            purchase_to_change.status = -1
        elif request.data['orderStatus'] == 'accepted':
            purchase_to_change.status = 0
        elif request.data['orderStatus'] == 'at_cargo':
            purchase_to_change.status = 1
        elif request.data['orderStatus'] == 'delivered':
            purchase_to_change.status = 2
        else:
            return Response({'is_successful': False, 'message':'Please provide a valid orderStatus, options are: cancelled, accepted, at_cargo, delivered'})

        purchase_to_change.save()

        return Response({'is_successful': True})