from API.models.product import ShoppingCartItem
from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User, Product, Category, Subcategory, User, Vendor, Brand, Customer
from ..views.shopping_cart import *
from ..utils.crypto import Crypto

user = None
product_id_for_test = 7

# Class for testing the CRUD operations of the Shopping Cart endpoint
class ShoppingCartItemTest(TestCase):
    def setUp(self):
        self.client = APIClient()
        # register a mock user
        global user
        salt = Crypto().getSalt()
        password_hash = Crypto().getHashedPassword("12345678", salt)
        user = User.objects.create(username="testuser", email="test@mail.com", role = 1,
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
        b = Brand.objects.create(id=1, name="Mavi")
        v = Vendor.objects.create(id=1, user = user, first_name="omerfaruk", last_name="deniz",rating_count=10,total_rating=3)
        global product_id_for_test
        Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt2", price = 100, 
            creation_date = "2019-08-20T07:22:34Z",total_rating = 20, discount=0.1,
            rating_count = 5, stock_amount = 10, short_description = "yaza özel",long_description = "gerçekten yaza özel yav", subcategory = s, brand = b, vendor = v)
    
    # test adding a shopping cart item to the database
    def test_add_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        # get the response for a POST request to the /shoppingcart endpoint
        response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        
    # test getting a shopping cart item to the database
    def test_get_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        # get the response for a POST request to the /shoppingcart endpoint
        post_response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        sc_item_id = post_response.data["sc_item_id"]
        # get the response for a GET request to the /shoppingcart/:sc_item_id endpoint
        response = self.client.get(reverse(manage_specific_shopping_cart_item, args = [user.id, sc_item_id]))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        
    # test getting all shopping cart items for the user from the database
    def test_get_all_shopping_cart_items(self):
        # get the response for a GET request to the /shoppingcart endpoint
        response = self.client.get(reverse(manage_shopping_cart_items, args = [user.id]))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    # test deleting a single shopping cart item of the customer from the database
    def test_delete_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        # get the response for a POST request to the /shoppingcart endpoint
        post_response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        sc_item_id = post_response.data["sc_item_id"]
        # get the response for a DELETE request to the /shoppingcart/:sc_item_id endpoint
        response = self.client.delete(reverse(manage_specific_shopping_cart_item, args = [user.id, sc_item_id]))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    # test updating a single shopping cart item of the customer from the database
    def test_update_shopping_cart_item(self):
        sc_item = {
            "customer_id": user.id,
            "product_id": product_id_for_test,
            "amount": 1
        }
        # get the response for a POST request to the /shoppingcart endpoint
        post_response = self.client.post(reverse(manage_shopping_cart_items, args = [user.id]), sc_item, 'json')
        sc_item_id = post_response.data["sc_item_id"]
        # get the response for a UPDATE request to the /shoppingcart/:sc_item_id endpoint
        response = self.client.put(reverse(manage_specific_shopping_cart_item, args = [user.id, sc_item_id]), sc_item, 'json')
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
