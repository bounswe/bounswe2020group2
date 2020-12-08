from django.db import models
import enum

class User (models.Model): 
    email = models.CharField(max_length=255)
    password_hash = models.CharField(max_length=255)
    password_salt = models.CharField(max_length=255)
    register_date = models.DateTimeField(auto_now_add=True)
    google_register = models.BooleanField(default=False)
    is_verified = models.BooleanField(default=False)
    username = models.CharField(max_length=255)
    role = models.IntegerField(default=1)
    def __str__(self):
        return self.username

class Customer (models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255)

#admin
class Admin (models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)

#vendor
class Vendor (models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255)

#address
class Address (models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    title = models.CharField(max_length=255)
    phone_number = models.CharField(max_length=255)
    address = models.CharField(max_length=255)
    province = models.CharField(max_length=255)
    city = models.CharField(max_length=255)
    country = models.CharField(max_length=255)