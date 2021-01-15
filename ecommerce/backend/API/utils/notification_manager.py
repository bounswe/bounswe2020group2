from ..models import Notification, Purchase, Product, Vendor, User, Customer, ImageUrls
from ..utils import order_status_to_str
import json

def notifyStatusChange(old_order_status, purchase_to_change):
    argument = {
        "purchase_id": purchase_to_change.pk,
        "old_status": order_status_to_str(old_order_status),
        "new_status": order_status_to_str(purchase_to_change.status),
        "product": {
            "id": purchase_to_change.product.pk,
            "title": purchase_to_change.product.name,
            "price": purchase_to_change.unit_price,
            "amount": purchase_to_change.amount,
            "image_url": ImageUrls.objects.filter(product=purchase_to_change.product).filter(index=0).first().image_url,
            "vendor": {
                "id": purchase_to_change.vendor.pk,
                "name": purchase_to_change.vendor.first_name + " " + purchase_to_change.vendor.last_name
            }
        }
    }
    notification = Notification(user=purchase_to_change.order.user, 
        is_seen=False, notification_type=1, argument=json.dumps(argument))
    notification.save()