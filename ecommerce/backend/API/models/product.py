from django.db import models
from .user import User, Vendor
#category
class Category (models.Model):
    name = models.CharField(max_length=255)


#subcategory

class Subcategory (models.Model):
    name = models.CharField(max_length=255)
    category = models.ForeignKey(Category, on_delete=models.CASCADE)
    
#brand
class Brand (models.Model):
    name = models.CharField(max_length=255)

#product
class Product (models.Model):
    name = models.CharField(max_length=255)
    subcategory = models.ForeignKey(Subcategory, on_delete=models.CASCADE) #subcategory id
    price = models.FloatField()
    creation_date = models.DateTimeField(auto_now_add=True)
    vendor = models.ForeignKey(Vendor, on_delete=models.CASCADE) #vendor id  
    brand = models.ForeignKey(Brand, on_delete=models.CASCADE) #brand id  
    total_rating = models.IntegerField(default=0)
    rating_count = models.IntegerField(default=0)
    rating = models.FloatField(default=0)
    stock_amount = models.IntegerField()
    short_description = models.CharField(max_length=255)
    long_description = models.TextField(max_length=255,default='long description')
    discount = models.FloatField(default=0.1)
    is_deleted = models.BooleanField(default=False)

class ProductIndex (models.Model):
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    text = models.CharField(max_length=5000)

#img urls
class ImageUrls (models.Model):
    product = models.ForeignKey(Product, on_delete=models.CASCADE) #product id  
    image_url = models.CharField(max_length=255)
    index = models.IntegerField(default=0)


#productlist
class ProductList(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    name = models.CharField(max_length=255)

#productlistitem
class ProductListItem (models.Model):
    product_list = models.ForeignKey(ProductList, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)

#shoppingcartitem
class ShoppingCartItem (models.Model):
    customer = models.ForeignKey(User, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    amount = models.IntegerField()
