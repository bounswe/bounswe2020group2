from django.db import models

class Image(models.Model):
    image = models.BinaryField(null=False, blank=False)