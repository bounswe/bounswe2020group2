from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import user, User, Customer
from ..utils.crypto import Crypto

class TestAccount(TestCase):
    def setUp(self):
        self.client = APIClient()
        salt = Crypto().getSalt()
        password_hash = Crypto().getHashedPassword("12345678", salt)
        user = User.objects.create(username="testuser", email="test@mail.com", role = 1,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        Customer.objects.create(user=user, first_name="test", last_name="user")

    def test_req_format(self):
        response = self.client.get(reverse('login'))
        self.assertEqual(response.status_code, 405)


    def test_vendor_signup(self):
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
        self.assertEqual(response.data["successful"], True)

    def test_vendor_signup_corrupted_address(self):
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
                "city": "İstanbul",
                "country": "Türkiye",
                "phone": {
                    "country_code": "+90",
                    "number": "+90551727180"
                },
                "zip_code": "34347"
            }
        }
        response = self.client.post(reverse('vendor_signup'), body, 'json')
        self.assertEqual(response.data["successful"], False)

    def test_invalid_register(self):
        body = {
            'username': 'testuser',
            'email': 'test@mail.com',
            'password': '1234567',
            'firstname': 'test',
            'lastname': 'user'
        }
        response = self.client.post(reverse('register'), body, 'json')
        self.assertEquals(response.data["successful"], False)

    def test_register(self):
        body = {
            'username': 'testuser',
            'email': 'test@mail.com',
            'password': '12345678',
            'firstname': 'test',
            'lastname': 'user'
        }
        response = self.client.post(reverse('register'), body, 'json')
        usr = user.User.objects.filter(username='testuser').first()
        self.assertEqual(response.status_code, 200)
        self.assertEqual(usr.email, 'test@mail.com')

    def test_login_success(self):
        body = {
            'username': 'testuser',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)
        response = self.client.get(reverse('init'))
        self.assertEquals(response.status_code, 200)

    def test_response(self):
        body = {
            'username': 'tempuser',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], False)

    def test_auth(self):
        response = self.client.get(reverse('api'))
        self.assertEqual(response.status_code, 403)
    
    # test changing the password
    def test_password_change(self):
        # log in the user
        body = {
            'username': 'testuser',
            'password': '12345678'
        }
        # post a request to login endpoint
        response = self.client.post(reverse('login'), body, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)

        change_password_body = {
            'password': '12345678',
            'new_password': '87654321'
        }
        # post a request to changepassword endpoint
        response = self.client.post(reverse('changepassword'), change_password_body, 'json')
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
