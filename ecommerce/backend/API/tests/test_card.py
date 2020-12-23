from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User
from ..views.card import *

user = None
class CardTest(TestCase):
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
        #add cards to the test db
        for i in range(1,6):
            Card.objects.create(user=user, name=f'Garanti{i}', owner_name="OmerFaruk Deniz", 
                serial_number=((str(i)*4+'-')*4)[:-1], expiration_month=i, expiration_year=2020+i,cvv=111*i)
    
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
        response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
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
        post_response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        card_id = post_response.data["card_id"]
        response = self.client.get(reverse(manage_specific_card, args = [user.id, card_id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    def test_get_all_cards(self):
        response = self.client.get(reverse(manage_cards, args = [user.id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)

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
        post_response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        card_id = post_response.data["card_id"]
        response = self.client.delete(reverse(manage_specific_card, args = [user.id, card_id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
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
        post_response = self.client.post(reverse(manage_cards, args = [user.id]), card, 'json')
        card_id = post_response.data["card_id"]
        response = self.client.put(reverse(manage_specific_card, args = [user.id, card_id]), card, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
