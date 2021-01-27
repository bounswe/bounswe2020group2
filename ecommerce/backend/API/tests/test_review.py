from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User, Vendor, Product, Review, Purchase, Category, Subcategory, Brand, Address, Order, Customer
from ..utils import OrderStatus
import datetime
from ..utils.crypto import Crypto

class ReviewTests(TestCase):

    def setUp(self):
        self.client = APIClient()
        body = {
            'username': 'testuser',
            'email': 'test@mail.com',
            'password': '12345678',
            'firstname': 'test',
            'lastname': 'user'
        }

        salt = Crypto().getSalt()
        password_hash = Crypto().getHashedPassword("12345678", salt)
        user = User.objects.create(username="testuser", email="test@mail.com", role = 1,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        Customer.objects.create(user=user, first_name="test", last_name="user")

        u = User.objects.filter(username='testuser').first()
        self.uid = u.id
        
        user_v1 = User.objects.create(id=1000, username="testvendor1", email="test@mail.com", role = 2,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        user_v2 = User.objects.create(id=1001, username="testvendor2", email="testq@mail.com", role = 2,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)

        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        a = Address.objects.create(id=1, user=u, address="istanbul")
        b = Brand.objects.create(id=1, name="Mavi")
        v1 = Vendor.objects.create(id=1, user = user_v1, first_name="omerfaruk", last_name="deniz")
        v2 = Vendor.objects.create(id=2, user = user_v2, first_name="umut", last_name="öksüz")
        p1 = Product.objects.create(id=1, name = "Mavi T-shirt", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, 
            rating_count = 20, stock_amount = 10, short_description = "yaza özel", subcategory = s, brand = b, vendor = v1)
        p2 = Product.objects.create(id=2, name = "Mavi Pantolon", price = 100, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, 
            rating_count = 20, stock_amount = 10, short_description = "yaza özel", subcategory = s, brand = b, vendor = v1)
        order = Order.objects.create(id=1, user=u)
        Purchase.objects.create(order=order, product=p1, amount = 1, unit_price = 100, vendor=v1,
        status = OrderStatus.DELIVERED.value, address=a, purchase_date=datetime.date.today())
        body = {
            'username': 'testuser',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)

    
    def test_add_review(self):
        comment = {
            'user_id': self.uid,
            'product_id': 1,
            'vendor_id': None,
            'rating': 5,
            'comment': "Awesome!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)

    def test_add_review_not_purchased(self):
        comment = {
            'user_id': self.uid,
            'product_id': 2,
            'vendor_id': None,
            'rating': 5,
            'comment': "Awesome!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], False)
        self.assertEqual(response.data["status"]["message"], "You cannot review a product that you have not bought.")

    def test_add_multiple_review(self):
        comment = {
            'user_id': self.uid,
            'product_id': 1,
            'vendor_id': None,
            'rating': 5,
            'comment': "Awesome!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        response = self.client.post(reverse('review'), comment, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], False)
        self.assertEqual(response.data["status"]["message"], "You have already reviewed this product.")

    def test_review_vendor(self):
        comment = {
            'user_id': self.uid,
            'product_id': None,
            'vendor_id': 1000,
            'rating': 5,
            'comment': "Awesome vendor!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)

    def test_review_vendor_not_purchased(self):
        comment = {
            'user_id': self.uid,
            'product_id': None,
            'vendor_id': 1001,
            'rating': 5,
            'comment': "Awesome vendor!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], False)
        self.assertEqual(response.data["status"]["message"], "You cannot review a vendor that you have not purchased a prouct from.")

    def test_add_multiple_review_vendor(self):
        comment = {
            'user_id': self.uid,
            'product_id': None,
            'vendor_id': 1000,
            'rating': 5,
            'comment': "Awesome vendor!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        response = self.client.post(reverse('review'), comment, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], False)
        self.assertEqual(response.data["status"]["message"], "You have already reviewed this vendor.")

    def test_delete_review(self):
        comment = {
            'user_id': self.uid,
            'product_id': 1,
            'vendor_id': None,
            'rating': 5,
            'comment': "Awesome!"
        }
        response = self.client.post(reverse('review'), comment, 'json')
        review_id = response.data["review"]["id"]
        body = { 'id': review_id }
        response = self.client.delete(reverse('review'), body, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(response.data["status"]["message"], "Review is deleted successfully.")