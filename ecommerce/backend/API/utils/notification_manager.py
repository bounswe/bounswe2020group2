from ..models import *
from ..utils import order_status_to_str
import json

def notifyStatusChange(old_order_status, purchase_to_change):
    image_url_obj = ImageUrls.objects.filter(product=purchase_to_change.product).filter(index=0).first()
    image_url = image_url_obj.image_url if image_url_obj is not None else None
    argument = {
        "purchase_id": purchase_to_change.pk,
        "old_status": order_status_to_str(old_order_status),
        "new_status": order_status_to_str(purchase_to_change.status),
        "product": {
            "id": purchase_to_change.product.pk,
            "title": purchase_to_change.product.name,
            "price": purchase_to_change.unit_price,
            "amount": purchase_to_change.amount,
            "image_url": image_url,
            "vendor": {
                "id": purchase_to_change.vendor.pk,
                "name": purchase_to_change.vendor.first_name + " " + purchase_to_change.vendor.last_name
            }
        }
    }
    notification = Notification(user=purchase_to_change.order.user, 
        is_seen=False, notification_type=1, argument=json.dumps(argument))
    notification.save()

def notifyPriceChange(product, new_price, old_price):
    image_url_obj = ImageUrls.objects.filter(product=product).filter(index=0).first()
    image_url = image_url_obj.image_url if image_url_obj is not None else None
    argument = {
        "id": product.pk,
        "title": product.name,
        "old_price": old_price,
        "new_price": new_price,
        "short_description": product.short_description,
        "image_url": image_url,
        "vendor": {
            "id": product.vendor.pk,
            "name": product.vendor.first_name + " " + product.vendor.last_name
        }
    }
    products = ProductListItem.objects.filter(product=product).all()
    for productItem in products:
        notification = Notification(user=productItem.product_list.user, is_seen=False, notification_type = 2, argument=json.dumps(argument))
        notification.save()