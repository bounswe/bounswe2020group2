from rest_framework.test import APIClient
from django.test import TestCase
from rest_framework import status
from rest_framework.renderers import JSONRenderer
from django.urls import reverse
from django.test import tag,Client
from ..models import *
from ..models.purchase import Order
from ..utils.crypto import Crypto
from ..views.recommendation import recommend_products


class TestRecommendation(TestCase):
    def setUp(self):
    
        self.client = APIClient()
        # register a mock user
        global user
        salt = Crypto().getSalt()
        password_hash = Crypto().getHashedPassword("12345678", salt)
        user = User.objects.create(id=1,username="testuser", email="test@mail.com", role = 1,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        Customer.objects.create(user=user, first_name="test", last_name="user")

        user = User.objects.filter(username='testuser').first()
        body = {
            'username': 'testuser',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)
        
        # create mock objects necessary for adding an item
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        s2 = Subcategory.objects.create(id=11, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        v = Vendor.objects.create(id=1, user = user, first_name="omerfaruk", last_name="deniz",rating_count=10,total_rating=3)

        p = Product.objects.create(id=1, name = "Mavi T-shirt2", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
        p1 = Product.objects.create(id=2, name = "Mavi T-shirt3", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
        p2 = Product.objects.create(id=3, name = "Mavi T-shirt4", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
        p3 = Product.objects.create(id=4, name = "Mavi T-shirt5", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
        p4 = Product.objects.create(id=5, name = "Mavi T-shirt6", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
        Address.objects.create(id=2,user=user, title=f"Address-1", name="OmerFaruk", surname="Deniz", 
            address= "Mahalle Sokak Sk. No 23/8", province= "Sarıyer", city= "Istanbul", 
            phone_country_code= "+90", phone_number= "5351234567", country= "Turkey", zip_code= "34344")
        p21 = Product.objects.create(id=6, name = "other1", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s2, brand = b, vendor = v)
        p22 = Product.objects.create(id=7, name = "other2", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s2, brand = b, vendor = v)
        order = Order.objects.create(id=1, user_id=1)
        purchase = Purchase.objects.create(id=1,amount=3,unit_price=22.11,status=0,purchase_date='2019-08-20 10:22:34+03',address_id=2,product=p,vendor=v,order_id=1)
        order = Order.objects.create(id=2, user_id=1)
        purchase = Purchase.objects.create(id=2,amount=3,unit_price=22.11,status=0,purchase_date='2019-08-20 10:22:34+03',address_id=2,product=p21,vendor=v,order_id=2)

    @tag('recommend')
    def test_1_recommendation(self):
        response = self.client.get(reverse(recommend_products))

        self.assertEqual(len(response.data["products"]), 2)


