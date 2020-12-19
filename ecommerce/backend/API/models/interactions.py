from django.db import models
from .product import Product
from .user import User, Vendor


#review
class Review(models.Model):
    vendor = models.ForeignKey(Vendor, on_delete=models.CASCADE, null=True)
    product = models.ForeignKey(Product, on_delete=models.CASCADE, null=True)
    rating = models.IntegerField()
    comment = models.CharField(max_length=400)
    reviewed_by = models.ForeignKey(User, on_delete=models.CASCADE, null=True)
    review_date = models.DateTimeField(auto_now_add=True)
    is_deleted = models.BooleanField(default=False)

    def __str__(self):
        return self.comment
