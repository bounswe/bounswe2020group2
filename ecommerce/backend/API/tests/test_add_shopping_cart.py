import json
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse
from ..models import Product, Category, Subcategory, User, Vendor, Brand, ShoppingCartItem, Customer
from ..views.shopping_cart import add_shopping_cart_item 

client = Client()
product_id_for_test = 1
customer_id_for_test = 1

class AddShoppingCart(TestCase):
    def setUp(self):
        user = User.objects.create(id=customer_id_for_test, username="customeruser", email="customeruser@gmail.com", role = 1)
        customer = Customer.objects.create(id=customer_id_for_test, user = user, first_name="customername", last_name="customerlastname")
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=2, username="vendoruser", email="vendoruser@gmail.com", role = 2)
        v = Vendor.objects.create(user = u, first_name="vendorname", last_name="vendorlastname")
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", image_url = "image_url1", total_rating = 4, 
            rating_count = 20, stock_amount = 10, description = "yaza özel", subcategory = s, brand = b, vendor = v)

    def test_add_item_shopping_cart(self):
        body = {
            'amount': '2',
            'productId': '1'
        }
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        shopping_cart = ShoppingCartItem.objects.filter(customer_id=customer_id_for_test).filter(product_id=product_id_for_test).first()
        self.assertEqual(response.status_code, 200)
        self.assertEqual(int(body["amount"]), shopping_cart.amount)

    def test_reach_stock_amount(self):
        body = {
            'amount': '11',
            'productId': '1'
        }
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.content, b'"Stock Amount is reached"')

    def test_non_negative_shopping_cart(self):
        body = {
            'amount': '-2',
            'productId': '1'
        }
        response = client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.content, b'"Amount cannot be negative"')

    def test_update_shopping_cart(self):
        body1 = {
            'amount': '2',
            'productId': '1'
        }
        client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body1)

        body2 = {
            'amount': '5',
            'productId': '1'
        }
        client.post(reverse(add_shopping_cart_item, args = [customer_id_for_test]), data=body2)
        shopping_cart = ShoppingCartItem.objects.filter(customer_id=customer_id_for_test).filter(product_id=product_id_for_test).first()
        self.assertEqual(int(body1["amount"]) + int(body2["amount"]), shopping_cart.amount)
