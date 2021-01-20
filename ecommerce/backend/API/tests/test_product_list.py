import json
from rest_framework.test import APIClient
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse

from ..models import Product, Category, Subcategory, User, Vendor, Brand, ShoppingCartItem, Customer, Address, Purchase, Order, Card, ProductList, ProductListItem
from ..views.product_list import product_list_create, product_list_delete, manage_product_list_item
from ..utils.crypto import Crypto
from ..utils import order_status

client = APIClient()
product_id_for_test = 1
product_2_id_for_test = 2
customer_id_for_test = 1
vendor_id_for_test = 3
address_id_for_test = 1
list_id_for_test = 1
list_name = "My favourite list"

vendor_pk_for_test = 1

unit_price = 100
amount = 2
discount = 0.1
crypto = Crypto()
p = None

class ProductListTest(TestCase):
    def setUp(self):
        password = '12345678'
        salt = crypto.getSalt()
        password_hash = crypto.getHashedPassword(password, salt)

        user = User.objects.create(id=customer_id_for_test, username="customeruser", email="customeruser@gmail.com", role = 1, \
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        customer = Customer.objects.create(user = user, first_name="customerusername", last_name="customeruserlastname")
        address = Address.objects.create(id=address_id_for_test, user=user, title=f"Address-1", name="test", surname="test", 
                address= "Mahalle Sokak Sk. No 23/8", province= "Sarıyer", city= "Istanbul", 
                phone_country_code= "+90", phone_number= "5351234567", country= "Turkey", zip_code= "34344")
        
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=vendor_id_for_test, username="vendoruser", email="vendoruser@gmail.com", role = 2)
        v = Vendor.objects.create(id=vendor_pk_for_test, user = u, first_name="vendorname", last_name="vendorlastname")
        p1 = Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = unit_price, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, short_description="", long_description="",
            rating_count = 20, stock_amount = 10, subcategory = s, brand = b, vendor = v, discount = discount)
        p2 = Product.objects.create(id=product_2_id_for_test, name = "Mavi T-shirt", price = unit_price, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, short_description="", long_description="",
            rating_count = 20, stock_amount = 10, subcategory = s, brand = b, vendor = v, discount = discount)

    def login_credentials_settings(self):
        body = {
            'username': 'customeruser',
            'password': '12345678'
        }
        response = client.post(reverse('login'), body, 'json')
        token_customer = response.data["user"]["token"]
        return token_customer

    def test_post_product_list(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        body = {'name': 'My favourite list'}
        response = client.post(reverse(product_list_create), body, 'json')
        product_list = ProductList.objects.order_by('-pk')[0]

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(int(response.data["id"]), product_list.pk)

    def product_list(self):
        ProductList.objects.create(id=list_id_for_test, user_id=customer_id_for_test, name=list_name)
        ProductListItem.objects.create(product_list_id=list_id_for_test, product_id=product_id_for_test)
        ProductListItem.objects.create(product_list_id=list_id_for_test, product_id=product_id_for_test)

    def test_get_product_list(self):
        self.product_list()

        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        response = client.get(reverse(product_list_create))

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(int(response.data["lists"][0]["list_id"]), list_id_for_test)
        self.assertEqual(response.data["lists"][0]["name"], list_name)

    def test_delete_product_list(self):
        self.product_list()

        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        response = client.delete(reverse(product_list_delete, args = [list_id_for_test]))

        product_list = ProductList.objects.filter(id=list_id_for_test)
        product_list_items = ProductListItem.objects.filter(product_list_id=list_id_for_test)


        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(len(product_list), 0)
        self.assertEqual(len(product_list_items), 0)

    def just_product_list(self):
        ProductList.objects.create(id=list_id_for_test, user_id=customer_id_for_test, name=list_name)

    def test_add_product__to_list(self):
        self.just_product_list()

        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        response = client.post(reverse(manage_product_list_item, args = [list_id_for_test, product_id_for_test]))

        product_list_items = ProductListItem.objects.filter(product_list_id=list_id_for_test)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(len(product_list_items), 1)
