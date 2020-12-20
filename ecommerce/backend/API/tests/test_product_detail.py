import json
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse
from ..models import Product, Category, Subcategory, User, Vendor, Brand
from ..serializers.product_serializer import ProductResponseSerializer
from ..views.product import product_detail

client = Client()
product_id_for_test = 1

class ProductDetail(TestCase):
    def setUp(self):
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=1, username="omer", email="omer@gmail.com", role = 2)
        v = Vendor.objects.create(id=1, user = u, first_name="omerfaruk", last_name="deniz",rating_count=10,total_rating=3)
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)

    def test_product_detail(self):
        response = client.get(reverse(product_detail, args = [product_id_for_test]))
        product = Product.objects.filter(id = product_id_for_test)
        serializer = ProductResponseSerializer(product, many=True)
        self.assertEqual(response.data, serializer.data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
