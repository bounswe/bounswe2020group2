from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User
from ..views.message import *
from ..utils.crypto import Crypto

user = None

# Class for testing the CRUD operations of the Message endpoint
class MessageTest(TestCase):
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
    
    # test sending a message
    def test_send_message(self):
        message = {
            "receiver_id": 11,
            "text": "Do you have the product in the attachment?",
            "attachment_url": "https://www.adidas.com.tr/tr/superstar-ayakkab%C4%B1/EG4959.html"
        }
        # get the response for a POST request to the /messages endpoint
        response = self.client.post(reverse(manage_messages), message, 'json')
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    # test getting all messages of a user from the database
    def test_get_all_messages(self):
        # get the response for a GET request to the /messages endpoint
        response = self.client.get(reverse(manage_messages))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        
