import json
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse
from ..models import Product, Category, Subcategory, User, Vendor, Brand, ShoppingCartItem, Customer
from ..serializers.shopping_cart_item_serializer import ShoppingCartItemSerializer
from ..views.shopping_cart import list_shopping_cart

client = Client()
product_id_for_test = 1
customer_id_for_test = 1

class ListShoppingCart(TestCase):
    def setUp(self):
        user = User.objects.create(id=customer_id_for_test, username="omer", email="omer@gmail.com", role = 1)
        customer = Customer.objects.create(id=customer_id_for_test, user = user, first_name="omerfaruk", last_name="deniz")
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=2, username="furkan", email="furkan@gmail.com", role = 2)
        v = Vendor.objects.create(id=1, user = u, first_name="omerfaruk", last_name="deniz")
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, 
            rating_count = 20, stock_amount = 10, short_description = "yaza özel", subcategory = s, brand = b, vendor = v)
        ShoppingCartItem.objects.create(customer_id = customer_id_for_test, product_id=product_id_for_test, amount=1)

    def test_list_shopping_cart(self):
        response = client.get(reverse(list_shopping_cart, args = [customer_id_for_test]))
        sc_items = ShoppingCartItem.objects.filter(customer_id = customer_id_for_test).values('id', 'product_id', 'amount')
        serializer = ShoppingCartItemSerializer(sc_items, many=True)
        self.assertEqual(response.data, serializer.data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

