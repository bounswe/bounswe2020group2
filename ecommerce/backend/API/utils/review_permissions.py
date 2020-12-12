from API.models.user import *
from API.models.product import *
from API.models import Purchase, Review 
from .order_status import OrderStatus


def can_review_product(user_id, product_id):
    user = User.objects.filter(pk=user_id).first()
    product = Product.objects.filter(pk=product_id).first()
    if product is None or user is None:
        return (False, "Product is not found.")
    order = Purchase.objects.filter(user=user).filter(product=product).filter(status=OrderStatus.DELIVERED.value).first()
    review = Review.objects.filter(reviewed_by=user).filter(product=product).filter(is_deleted=False).first()
    if order is None:
        return (False, "You cannot review a product that you have not bought.")
    elif review is not None:
        return(False, "You have already reviewed this product.")
    else:
        return (True, "Success")

def can_review_vendor(user_id, vendor_id):
    user = User.objects.filter(pk=user_id).first()
    vendor = Vendor.objects.filter(pk=vendor_id).first()
    if user is None or vendor is None:
        return (False, "Invalid user or vendor")
    orders = Purchase.objects.filter(user=user).filter(status=OrderStatus.DELIVERED.value).all()
    bought_from_vendor = False
    for order in orders:
        if order.product.vendor == vendor:
            bought_from_vendor = True
            break
    if not bought_from_vendor:
        return (False, "You cannot review a vendor that you have not purchased a prouct from.")
    review = Review.objects.filter(reviewed_by=user).filter(vendor=vendor).filter(is_deleted=False).first()
    if review is not None:
        return (False, "You have already reviewed this vendor.")
    return(True, "Success")



