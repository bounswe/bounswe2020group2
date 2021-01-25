from API.models.user import *
from API.models.product import *
from API.models import Purchase, Review, Order
from .order_status import OrderStatus


def can_review_product(user_id, product_id):
    user = User.objects.filter(pk=user_id).first()
    product = Product.objects.filter(pk=product_id).first()
    if product is None or user is None:
        return (False, "Product is not found.")
    orders = Order.objects.filter(user=user).all()
    purchased = False
    for order in orders:
        if Purchase.objects.filter(order=order).filter(product=product).filter(status=OrderStatus.DELIVERED.value).first() is not None:
            purchased = True
            break
    review = Review.objects.filter(reviewed_by=user).filter(product=product).filter(is_deleted=False).first()
    if not purchased:
        return (False, "You cannot review a product that you have not bought.")
    elif review is not None:
        return(False, "You have already reviewed this product.")
    else:
        return (True, "Success")

def can_review_vendor(user_id, vendor_id):
    user = User.objects.filter(pk=user_id).first()
    vendor = Vendor.objects.filter(user_id=vendor_id).first()
    if user is None or vendor is None:
        return (False, "Invalid user or vendor")
    
    orders = Order.objects.filter(user=user).all()
    purchased_from_vendor = False
    for order in orders:
        if Purchase.objects.filter(order=order).filter(vendor=vendor).filter(status=OrderStatus.DELIVERED.value).first() is not None:
            purchased_from_vendor = True
            break

    if not purchased_from_vendor:
        return (False, "You cannot review a vendor that you have not purchased a prouct from.")
    review = Review.objects.filter(reviewed_by=user).filter(vendor=vendor).filter(is_deleted=False).first()
    if review is not None:
        return (False, "You have already reviewed this vendor.")
    return(True, "Success")



