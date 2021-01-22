from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User, Customer
from ..views.message import *
from ..utils.crypto import Crypto

sender = None
receiver = None
# Class for testing the CRUD operations of the Message endpoint
class MessageTest(TestCase):
    def setUp(self):
        self.client = APIClient()
        # register a mock sender
        global sender, receiver
        salt = Crypto().getSalt()
        password_hash = Crypto().getHashedPassword("sender123", salt)
        sender = User.objects.create(username="testsender", email="sender@mail.com", role = 1,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        Customer.objects.create(user=sender, first_name="s_first", last_name="s_last")
        sender = User.objects.filter(username='testsender').first()
        body = {
            'username': 'testsender',
            'password': 'sender123'
        }
        response = self.client.post(reverse('login'), body, 'json')
        token = response.data["user"]["token"]
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)

        # register a mock receiver
        salt = Crypto().getSalt()
        password_hash = Crypto().getHashedPassword("receiver123", salt)
        receiver = User.objects.create(username="testreceiver", email="receiver@mail.com", role = 1,
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        Customer.objects.create(user=sender, first_name="r_first", last_name="s_last")
        receiver = User.objects.filter(username='testreceiver').first()