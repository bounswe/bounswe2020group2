from django.db import models
from .user import User, Address
from .product import Product

#purchase
class Purchase (models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    amount = models.IntegerField()
    unit_price = models.FloatField()
    name = models.CharField(max_length=255)
    status = models.IntegerField()
    purchase_date = models.DateTimeField(auto_now_add=True)
    address = models.ForeignKey(Address, on_delete=models.CASCADE)

    def __str__(self):
        return self.name