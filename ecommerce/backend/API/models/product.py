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
    price = models.IntegerField()
    creation_date = models.DateTimeField(auto_now_add=True)
    vendor = models.ForeignKey(Vendor, on_delete=models.CASCADE) #vendor id  
    image_url = models.CharField(max_length=255)
    brand = models.ForeignKey(Brand, on_delete=models.CASCADE) #brand id  
    total_rating = models.IntegerField()
    rating_count = models.IntegerField()
    stock_amount = models.IntegerField()
    description = models.CharField(max_length=255)

#productlist
class ProductList(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    name = models.CharField(max_length=255)
#productlistitem
class ProductListItem (models.Model):
    name = models.CharField(max_length=255)
    product_list = models.ForeignKey(ProductList, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)

    def __str__(self):
        return self.name

#shoppingcartitem
class ShoppingCartItem (models.Model):
    customer = models.ForeignKey(User, on_delete=models.CASCADE)
    product_id = models.IntegerField()
    amount = models.IntegerField()
