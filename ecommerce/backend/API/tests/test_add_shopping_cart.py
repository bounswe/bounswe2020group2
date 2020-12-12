import json
from rest_framework.test import APIClient
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse
from ..models import Product, Category, Subcategory, User, Vendor, Brand, ShoppingCartItem, Customer
from ..views.shopping_cart import add_shopping_cart_item 
from ..utils.crypto import Crypto

client = APIClient()
product_id_for_test = 1
customer_id_for_test = 1
forbidden_id_for_test = 2
vendor_id_for_test = 3
crypto = Crypto()

class AddShoppingCart(TestCase):
    def setUp(self):
        password = '12345678'
        salt = crypto.getSalt()
        password_hash = crypto.getHashedPassword(password, salt)

        user = User.objects.create(id=customer_id_for_test, username="customeruser", email="customeruser@gmail.com", role = 1, \
                                            password_salt=salt, password_hash=password_hash)
        customer = Customer.objects.create(user = user, first_name="customerusername", last_name="customeruserlastname")
        
        forbidden_user = User.objects.create(id=forbidden_id_for_test, username="forbiddenuser", email="forbiddenuser@gmail.com", \
                                                            role = 1, password_salt=salt, password_hash=password_hash)
        forbidden_customer = Customer.objects.create(user = forbidden_user, first_name="forbiddenusername", last_name="forbiddenuserlastname")

        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=vendor_id_for_test, username="vendoruser", email="vendoruser@gmail.com", role = 2)
        v = Vendor.objects.create(user = u, first_name="vendorname", last_name="vendorlastname")
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, 
            rating_count = 20, stock_amount = 10, short_description = "yaza özel", subcategory = s, brand = b, vendor = v)


    def test_forbidden_user(self):
        body = {
            'username': 'forbiddenuser',
            'password': '12345678'
        }
        response = client.post(reverse('login'), body, 'json')
        token_forbidden = response.data["user"]["token"]
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(token_forbidden))

        body = {
            'amount': '2',
            'productId': '1'
        }
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        self.assertEqual(response.status_code, 200)

    def login_credentials_settings(self):
        body = {
            'username': 'customeruser',
            'password': '12345678'
        }
        response = client.post(reverse('login'), body, 'json')
        token_customer = response.data["user"]["token"]
        return token_customer

    def test_add_item_shopping_cart(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        body = {
            'amount': '2',
            'productId': '1'
        }
        
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        shopping_cart = ShoppingCartItem.objects.filter(customer_id=customer_id_for_test).filter(product_id=product_id_for_test).first()
        self.assertEqual(response.status_code, 200)
        self.assertEqual(int(body["amount"]), shopping_cart.amount)
        self.assertEqual(response.data["succesful"], True)
        

    def test_reach_stock_amount(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        body = {
            'amount': '11',
            'productId': '1'
        }

        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["message"], "Stock Amount is reached")

    def test_non_negative_shopping_cart(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        body = {
            'amount': '-1',
            'productId': '1'
        }
        
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["message"], "Amount cannot be negative")

    def test_update_shopping_cart(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        body1 = {
            'amount': '2',
            'productId': '1'
        }
        client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body1)

        body2 = {
            'amount': '5',
            'productId': '1'
        }
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body2)
        shopping_cart = ShoppingCartItem.objects.filter(customer_id=customer_id_for_test).filter(product_id=product_id_for_test).first()
        self.assertEqual(int(body1["amount"]) + int(body2["amount"]), shopping_cart.amount)
        self.assertEqual(response.data["succesful"], True)
