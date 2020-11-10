from django.db import models

# Create your models here.

class User (models.Model): 
    email = models.CharField(max_length=100)
    register_date = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.email
