import json
from rest_framework.test import APIClient
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse
from ..models import Product, Category, Subcategory, User, Vendor, Brand, ShoppingCartItem, Customer
from ..views.checkout import checkout_details
from ..utils.crypto import Crypto

client = APIClient()
product_id_for_test = 1
customer_id_for_test = 1
vendor_id_for_test = 3

price = 100
delivery_price = 7.9
amount = 2
discount = 0.1
crypto = Crypto()

class CheckoutSummaryTest(TestCase):
    def setUp(self):
        password = '12345678'
        salt = crypto.getSalt()
        password_hash = crypto.getHashedPassword(password, salt)

        user = User.objects.create(id=customer_id_for_test, username="customeruser", email="customeruser@gmail.com", role = 1, \
                                            password_salt=salt, password_hash=password_hash)
        customer = Customer.objects.create(user = user, first_name="customerusername", last_name="customeruserlastname")
        
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=vendor_id_for_test, username="vendoruser", email="vendoruser@gmail.com", role = 2)
        v = Vendor.objects.create(user = u, first_name="vendorname", last_name="vendorlastname")
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = price, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, short_description="", long_description="",
            rating_count = 20, stock_amount = 10, subcategory = s, brand = b, vendor = v, discount = discount)
        ShoppingCartItem.objects.create(customer_id = customer_id_for_test, product_id=product_id_for_test, amount=amount)


    def login_credentials_settings(self):
        body = {
            'username': 'customeruser',
            'password': '12345678'
        }
        response = client.post(reverse('login'), body, 'json')
        token_customer = response.data["user"]["token"]
        return token_customer

    def test_get_checkout_summary(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        response = client.get(reverse(checkout_details))

        self.assertEqual(response.status_code, 200)
        self.assertEqual('{:.2f}'.format(float(response.data["products_price"])), '{:.2f}'.format(price * amount))
        self.assertEqual('{:.2f}'.format(float(response.data["delivery_price"])), '{:.2f}'.format(delivery_price))
        self.assertEqual('{:.2f}'.format(float(response.data["discount"])), '{:.2f}'.format(price * amount * discount))
        self.assertEqual('{:.2f}'.format(float(response.data["total_price"])), '{:.2f}'.format(price * amount * (1 - discount) + delivery_price))