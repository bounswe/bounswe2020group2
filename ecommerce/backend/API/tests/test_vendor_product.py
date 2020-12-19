from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import *
from ..utils import OrderStatus
import datetime
from ..views.product import product_detail

class VendorProductTest(TestCase):

    def setUp(self):
        self.client = APIClient()
        body = {
            "username": "newvendor",
            "password": "12345678",
            "email": "newvendor@vendor.com",
            "firstname": "my",
            "lastname": "vendor",
            "phonenumber": "+905517217180",
            "address": {
                "title": "main address",
                "address": "adkmakdksadsakdsaldad",
                "city": "İstanbul",
                "province": "Sarıyer",
                "country": "Türkiye",
                "phone": {
                    "country_code": "+90",
                    "number": "+90551727180"
                },
                "zip_code": "34347"
            }
        }
        response = self.client.post(reverse('vendor_signup'), body, 'json')
        self.u = User.objects.create(email="othervendor@mailcom", password_hash="", password_salt="", google_register=False,is_verified=False, role=2,username="othervendor")
        self.v = Vendor.objects.create(user=self.u, first_name="other", last_name="vendor")
        self.c = Category.objects.create(id=4, name="Clothing")
        self.b = Brand.objects.create(id=1, name="Mavi")
        self.s = Subcategory.objects.create(id=10, category = self.c, name="Men's Fashion")
        self.s2 = Subcategory.objects.create(id=11, category = self.c, name="Sports")
        p1 = Product.objects.create(id=10, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, 
            rating_count = 20, stock_amount = 10, short_description = "yaza özel", subcategory = self.s, brand = self.b, vendor = self.v)
        body = {
            'username': 'newvendor',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)

    
    def test_vendor_add_product(self):
        body = {
            "name": "Los Angeles Lakers Old School v2",
            "price": 799.9,
            "stock_amount": 450,
            "short_description": "Sarı mor içsaha forması LA LAKERS",
            "long_description": "anoacnancacnoewcnwocnoınvroeı3vnoıwvnroeıvnıoernvoernvoernvoıenvoıernvoerınvıernvoıernvoıernvroeınvoıernvorıenvoeırnvoıernvoıernvenvoernvoıernverı",
            "discount": 0.12,
            "brand_id": 1,
            "subcategory_id": 10,
            "images": []
        }
        response = self.client.post(reverse('vendor_product'), body, 'json')
        self.assertEqual(response.data["status"]["successful"], True)

    def test_vendor_update_product(self):
        body = {
            "name": "Los Angeles Lakers Old School v2",
            "price": 799.9,
            "stock_amount": 450,
            "short_description": "Sarı mor içsaha forması LA LAKERS",
            "long_description": "anoacnancacnoewcnwocnoınvroeı3vnoıwvnroeıvnıoernvoernvoernvoıenvoıernvoerınvıernvoıernvoıernvroeınvoıernvorıenvoeırnvoıernvoıernvenvoernvoıernverı",
            "discount": 0.12,
            "brand_id": 1,
            "subcategory_id": 10,
            "images": []
        }
        response = self.client.post(reverse('vendor_product'), body, 'json')
        pid = response.data["id"]
        body = {
            "id": pid,
            "name": "Los Angeles Lakers Old School v3",
            "price": 799.9,
            "stock_amount": 450,
            "short_description": "Sarı mor içsaha forması LA LAKERS",
            "long_description": "anoacnancacnoewcnwocnoınvroeı3vnoıwvnroeıvnıoernvoernvoernvoıenvoıernvoerınvıernvoıernvoıernvroeınvoıernvorıenvoeırnvoıernvoıernvenvoernvoıernverı",
            "discount": 0.18,
            "brand_id": 1,
            "subcategory_id": 11,
            "images": []
        }
        response = self.client.put(reverse('vendor_product'), body, 'json')
        self.assertEqual(response.data["status"]["successful"], True)
        response = self.client.get(reverse(product_detail, args = [pid]))
        self.assertEqual(response.data["name"], "Los Angeles Lakers Old School v3")
        self.assertEqual(response.data["subcategory"]["name"], "Sports")

    def test_change_other_vendors_product(self):
        body = {
            "id": 10,
            "name": "Los Angeles Lakers Old School v3",
            "price": 799.9,
            "stock_amount": 450,
            "short_description": "Sarı mor içsaha forması LA LAKERS",
            "long_description": "anoacnancacnoewcnwocnoınvroeı3vnoıwvnroeıvnıoernvoernvoernvoıenvoıernvoerınvıernvoıernvoıernvroeınvoıernvorıenvoeırnvoıernvoıernvenvoernvoıernverı",
            "discount": 0.18,
            "brand_id": 1,
            "subcategory_id": 11,
            "images": []
        }
        response = self.client.put(reverse('vendor_product'), body, 'json')
        self.assertEqual(response.status_code, 403)

    def test_delete_product(self):
        body = {
            "name": "Los Angeles Lakers Old School v2",
            "price": 799.9,
            "stock_amount": 450,
            "short_description": "Sarı mor içsaha forması LA LAKERS",
            "long_description": "anoacnancacnoewcnwocnoınvroeı3vnoıwvnroeıvnıoernvoernvoernvoıenvoıernvoerınvıernvoıernvoıernvroeınvoıernvorıenvoeırnvoıernvoıernvenvoernvoıernverı",
            "discount": 0.12,
            "brand_id": 1,
            "subcategory_id": 10,
            "images": []
        }
        response = self.client.post(reverse('vendor_product'), body, 'json')
        pid = response.data["id"]
        body = {
            'id': pid
        }
        response = self.client.delete(reverse('vendor_product'), body, 'json')
        self.assertEqual(response.data["status"]["successful"], True)