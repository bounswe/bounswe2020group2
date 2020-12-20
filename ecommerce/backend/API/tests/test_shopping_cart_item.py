from API.models.product import ShoppingCartItem
from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User, Product, Category, Subcategory, User, Vendor, Brand
from ..views.shopping_cart import *

user = None
product_id_for_test = 7
class ShoppingCartItemTest(TestCase):
    def setUp(self):
        self.client = APIClient()
        body = {
            'username': 'testuser',
            'email': 'test@mail.com',
            'password': '12345678',
            'firstname': 'test',
            'lastname': 'user'
        }
        response = self.client.post(reverse('register'), body, 'json')
        global user
        user = User.objects.filter(username='testuser').first()
        body = {
            'username': 'testuser',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)
        
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        v = Vendor.objects.create(id=1, user = user, first_name="omerfaruk", last_name="deniz",rating_count=10,total_rating=3)
        global product_id_for_test
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt2", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
    
    def test_add_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["successful"], True)
    
    def test_get_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        post_response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        sc_item_id = post_response.data["sc_item_id"]
        response = self.client.get(reverse(manage_specific_shopping_cart_item, args = [user.id, sc_item_id]))
        self.assertEqual(response.status_code, 200)
    
    def test_get_all_shopping_cart_items(self):
        response = self.client.get(reverse(manage_shopping_cart_items, args = [user.id]))
        self.assertEqual(response.status_code, 200)

    def test_delete_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        post_response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        sc_item_id = post_response.data["sc_item_id"]
        response = self.client.delete(reverse(manage_specific_shopping_cart_item, args = [user.id, sc_item_id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["successful"], True)
    
    def test_update_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        post_response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        sc_item_id = post_response.data["sc_item_id"]
        response = self.client.put(reverse(manage_specific_shopping_cart_item, args = [user.id, sc_item_id]), sc_item, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["successful"], True)