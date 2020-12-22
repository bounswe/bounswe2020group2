from rest_framework.test import APIClient
from django.test import TestCase
from rest_framework import status
from rest_framework.renderers import JSONRenderer
from django.urls import reverse
from django.test import tag,Client
from ..models import *
from ..models.purchase import Order
from ..views.order import vendor_orders


class TestOrderStatus(TestCase):
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
                "name": "umut",
                "surname": "oksuz",
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
        self.u = User.objects.create(id = 1, email="othervendor@mailcom", password_hash="", password_salt="", google_register=False,is_verified=False, role=2,username="othervendor")
        self.v = Vendor.objects.create(user = self.u, first_name="omerfaruk", last_name="deniz",rating_count=10,total_rating=3)
        self.c = Category.objects.create(id=4, name="Clothing")
        self.b = Brand.objects.create(id=1, name="Mavi")
        self.s = Subcategory.objects.create(id=10, category = self.c, name="Men's Fashion")
        self.s2 = Subcategory.objects.create(id=11, category = self.c, name="Sports")
        p1 = Product.objects.create(id=1, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, 
            rating_count = 20, stock_amount = 10, short_description = "yaza özel", subcategory = self.s, brand = self.b, vendor = self.v)
        body = {
            'username': 'newvendor',
            'password': '12345678'
        }

        response = self.client.post(reverse('login'), body, 'json')
        user_id = response.data['user']['id']
        user= User.objects.filter(id=user_id).first()
        vendor= Vendor.objects.filter(user=user).first()
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)
        
    
        Address.objects.create(id=2,user=self.u, title=f"Address-1", name="OmerFaruk", surname="Deniz", 
            address= "Mahalle Sokak Sk. No 23/8", province= "Sarıyer", city= "Istanbul", 
            phone_country_code= "+90", phone_number= "5351234567", country= "Turkey", zip_code= "34344")
        order = Order.objects.create(id=1, user_id=1)
        purchase = Purchase.objects.create(id=1,amount=3,unit_price=22.11,status=0,purchase_date='2019-08-20 10:22:34+03',address_id=2,product=p1,vendor=vendor,order_id=1)
    @tag('order')
    def test_change_purchase_status(self):


        body = {"orderId": 1,'orderStatus':'at_cargo'}
        response = self.client.put(reverse('vendor_orders'), body ,'json')
    
        self.assertEqual(response.data['is_successful'], True)