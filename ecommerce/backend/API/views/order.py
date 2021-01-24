from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
import json

from ..utils import permissions, Role
from ..models import Product,Vendor,ImageUrls,Category,Subcategory,Purchase, Order
from ..serializers.address_serializer import AddressResponseSerializer
from ..serializers.product_serializer import ProductResponseSerializer
from ..serializers.purchase_serializer import PurchaseResponseSerializer
from ..utils.order_status import OrderStatus
from ..utils import notifyStatusChange


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
            address = serializer.data

            serializer = ProductResponseSerializer(purchase.product)
            product = serializer.data

            if purchase.status == -1:
                purchase_status = 'cancelled'
            elif purchase.status == 0:
                purchase_status = 'accepted'
            elif purchase.status == 1:
                purchase_status = 'at_cargo'
            elif purchase.status == 2:
                purchase_status = 'delivered'

            response = {'id':purchase.id,'amount':purchase.amount,'unit_price':purchase.unit_price,'total_price':total_price,'status':purchase_status,
            'purchase_date':purchase.purchase_date,'address':address, 'product':product}

            response_purchases.append(response)

        return Response({'status': {'successful': True, 'message':'Orders are uccessfully sent'},'orders':response_purchases})


    if request.method == 'PUT':

        if request.data['orderId'] == None:
            return Response({'status': {'successful': False, 'message':'Please provide orderId'}})

        if request.data['orderStatus'] == None:
            return Response({'status': {'successful': False, 'message':'Please provide orderStatus'}})
        
        vendor = Vendor.objects.filter(user=request.user).first()

        purchase_to_change = Purchase.objects.filter(id=request.data['orderId']).first()

        if purchase_to_change.vendor_id != vendor.id:
            return Response({'status': {'successful': False, 'message':'This order does not belong to you'}})

        old_order_status = purchase_to_change.status

        if request.data['orderStatus'] == 'cancelled':
            purchase_to_change.status = -1
        elif request.data['orderStatus'] == 'accepted':
            purchase_to_change.status = 0
        elif request.data['orderStatus'] == 'at_cargo':
            purchase_to_change.status = 1
        elif request.data['orderStatus'] == 'delivered':
            purchase_to_change.status = 2
        else:
            return Response({'status': {'successful': False, 'message':'Please provide a valid orderStatus, options are: cancelled, accepted, at_cargo, delivered'}})

        purchase_to_change.save()
        
        notifyStatusChange(old_order_status, purchase_to_change)

        return Response({'status': {'successful': True, 'message':'Order status is successfully changed'}})

@api_view(['GET'])
@permission_classes([permissions.AllowAnonymous]) 
def customer_order(request):
    response_purchases = []
    products_price = 0
    delivery_price = 7.90
    total_discount = 0
    total_price = 0
    my_orders = Order.objects.filter(user_id=request.user.pk) 

    for order in my_orders:
        total_price = 0
        products_price = 0
        total_discount = 0
        purchases = Purchase.objects.filter(order_id=order.pk)
        pch_serializer = PurchaseResponseSerializer(purchases, many=True)
        for pch in pch_serializer.data:
            status = pch.get("status")
            if status == 'cancelled':
                continue
            else:
                amount = pch.get("amount")
                price = pch.get("product")["price"]
                products_price += amount * price
                total_discount += amount * (price * pch.get("product")["discount"])
        
        response_purchases.append({'order_id':order.pk, 'order_all_purchase':pch_serializer.data, 'prices': {
                "products_price": '{:.2f}'.format(products_price),
                "delivery_price": '{:.2f}'.format(delivery_price),
                "discount": '{:.2f}'.format(total_discount),
                "total_price": '{:.2f}'.format(products_price + delivery_price - total_discount),
                } })

    return Response({'status': {'successful': True, 'message':'Orders are uccessfully sent'},'orders':response_purchases})
    