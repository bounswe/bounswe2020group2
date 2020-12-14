from rest_framework.test import APIClient
from django.test import TestCase
from rest_framework import status
from rest_framework.renderers import JSONRenderer
from django.urls import reverse
from django.test import tag,Client
from ..models import *


class TestSearchProduct(TestCase):
    def setUp(self):
        self.client = APIClient()
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=1, username="omer", email="omer@gmail.com", role = 2)
        v = Vendor.objects.create(id=1, user = u, first_name="omerfaruk", last_name="deniz",rating_count=10,total_rating=3)
        Product.objects.create(id=1, name = "Mavi T-shirt", price = 120, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
        b = Brand.objects.create(id=2, name="Mor")
        u = User.objects.create(id=2, username="burak", email="burak@coolmail.com", role = 2)
        v = Vendor.objects.create(id=2, user = u, first_name="burak", last_name="cetin",rating_count=10,total_rating=3)
        Product.objects.create(id=2, name = "Mor T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 22, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "kışa özel",long_description = "sıcak tutar", subcategory = s, brand = b, vendor = v)
    @tag('search')
    def test_no_filter(self):
        body = {"page_size": 5}
        response = self.client.post(reverse('search_products'), body ,'json')
        #print("RESPONSE")
        #print(response.data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data["products"]), 2)
    @tag('search')
    def test_subcategory(self):
        body = {"subcategory": 2}
        response = self.client.post(reverse('search_products'), body,'json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data["products"]), 0)
    @tag('search')
    def test_brand(self):
        body = {"brand": [2]}
        response = self.client.post(reverse('search_products'), body,'json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data["products"]), 1)
        self.assertEqual(response.data["products"][0]["id"], 2)
    @tag('search')
    def test_sort(self):
        body = {"sort_by": "price","sort_order":"decreasing"}
        response = self.client.post(reverse('search_products'), body,'json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data["products"]), 2)
        self.assertEqual(response.data["products"][1]["id"], 2)
