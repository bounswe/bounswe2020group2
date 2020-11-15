from django.db import models
from .product import Product
from .user import User

#rating 
class Rating(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    rating = models.IntegerField()

    def __str__(self):
        return self.rating


#comment
class Comment(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    comment = models.CharField(max_length=255)
    comment_date = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.comment