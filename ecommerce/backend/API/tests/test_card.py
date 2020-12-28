from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User, Customer
from ..views.card import *
from ..utils.crypto import Crypto

user = None

# Class for testing the CRUD operations of the Card endpoint
class CardTest(TestCase):
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
        #add cards to the test db
        for i in range(1,6):
            Card.objects.create(user=user, name=f'Garanti{i}', owner_name="OmerFaruk Deniz", 
                serial_number=((str(i)*4+'-')*4)[:-1], expiration_month=i, expiration_year=2020+i,cvv=111*i)
    
    # test adding a single card to the database
    def test_add_card(self):
        card = {
            "name": "Garanti6",
            "owner_name": "OmerFaruk Deniz",
            "serial_number": "6666-6666-6666-6666",
            "expiration_date": {
                "month": 6, "year": 2026
            },
            "cvv": 666
        }
        # get the response for a POST request to the /cards endpoint
        response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    # test getting a single card from the database
    def test_get_card(self):
        card = {
            "name": "Garanti7",
            "owner_name": "OmerFaruk Deniz",
            "serial_number": "5555-6666-1234-5678",
            "expiration_date": {
                "month": 6, "year": 2030
            },
            "cvv": 111
        }
        # get the response for a POST request to the /cards endpoint
        post_response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        card_id = post_response.data["card_id"]
        # get the response for a GET request to the /cards/:card_id endpoint
        response = self.client.get(reverse(manage_specific_card, args = [user.id, card_id]))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    # test getting all cards of the customer from the database
    def test_get_all_cards(self):
        # get the response for a GET request to the /cards endpoint
        response = self.client.get(reverse(manage_cards, args = [user.id]))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)

    # test deleting a single card of the customer from the database
    def test_delete_card(self):
        card = {
            "name": "Garanti8",
            "owner_name": "OmerFaruk Deniz",
            "serial_number": "5555-6666-1234-5678",
            "expiration_date": {
                "month": 6, "year": 2030
            },
            "cvv": 111
        }
        # get the response for a POST request to the /cards endpoint
        post_response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        card_id = post_response.data["card_id"]
        # get the response for a DELETE request to the /cards/:card_id endpoint
        response = self.client.delete(reverse(manage_specific_card, args = [user.id, card_id]))
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    # test updating a single card of the customer from the database
    def test_update_card(self):
        card = {
            "name": "Garanti9",
            "owner_name": "OmerFaruk Deniz",
            "serial_number": "5555-6666-1234-5678",
            "expiration_date": {
                "month": 6, "year": 2030
            },
            "cvv": 111
        }
        # get the response for a POST request to the /cards endpoint
        post_response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        card_id = post_response.data["card_id"]
        # get the response for a UPDATE request to the /cards/:card_id endpoint
        response = self.client.put(reverse(manage_specific_card, args = [user.id, card_id]), card, 'json')
        # if the response returns a 200 and a status is successful, then test is passed
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
