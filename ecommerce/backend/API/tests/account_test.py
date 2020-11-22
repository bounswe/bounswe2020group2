from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from API.models import user

class TestAccount(TestCase):
    def setUp(self):
        self.client = APIClient()

    def test_req_format(self):
        response = self.client.get(reverse('login'))
        self.assertEqual(response.status_code, 405)

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
            'email': 'test@mail.com',
            'password': '12345678',
            'firstname': 'test',
            'lastname': 'user'
        }
        response = self.client.post(reverse('register'), body, 'json')
        body = {
            'username': 'testuser',
            'password': '12345678'
        }
        response = self.client.post(reverse('login'), body, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["success"], True)
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
        self.assertEqual(response.data["status"]["success"], False)

    def test_auth(self):
        response = self.client.get(reverse('api'))
        self.assertEqual(response.status_code, 403)