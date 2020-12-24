from rest_framework.test import APIClient
from django.test import TestCase
from django.urls import reverse
from ..models import User
from ..views.address import *

user = None
class AddressTest(TestCase):
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
        #add addresses to the test db
        for i in range(1,6):
            Address.objects.create(user=user, title=f"Address-{i}", name="OmerFaruk", surname="Deniz", 
                address= "Mahalle Sokak Sk. No 23/8", province= "Sarıyer", city= "Istanbul", 
                phone_country_code= "+90", phone_number= "5351234567", country= "Turkey", zip_code= "34344")
    
    def test_add_address(self):
        address = {
            "title": "Address6",
            "name": "OmerFaruk",
            "surname": "Deniz",
            "address": "Mahalle Sokak Sk. No 23/8",
            "province": "Sarıyer",
            "city": "Istanbul",
            "phone": {"country_code": "+90", "number": "5351234567"},
            "country": "Turkey",
            "zip_code": "34344"
        }
        response = self.client.post(reverse(manage_addresses, args = [user.id]), address, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    def test_get_address(self):
        address = {
            "title": "Address7",
            "name": "OmerFaruk",
            "surname": "Deniz",
            "address": "Mahalle Sokak Sk. No 23/8",
            "province": "Sarıyer",
            "city": "Istanbul",
            "phone": {"country_code": "+90", "number": "5351234567"},
            "country": "Turkey",
            "zip_code": "34344"
        }
        post_response = self.client.post(reverse(manage_addresses, args = [user.id]), address, 'json')
        address_id = post_response.data["address_id"]
        response = self.client.get(reverse(manage_specific_address, args = [user.id, address_id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    def test_get_all_addresses(self):
        response = self.client.get(reverse(manage_addresses, args = [user.id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)

    def test_delete_address(self):
        address = {
            "title": "Address8",
            "name": "OmerFaruk",
            "surname": "Deniz",
            "address": "Mahalle Sokak Sk. No 23/8",
            "province": "Sarıyer",
            "city": "Istanbul",
            "phone": {"country_code": "+90", "number": "5351234567"},
            "country": "Turkey",
            "zip_code": "34344"
        }
        post_response = self.client.post(reverse(manage_addresses, args = [user.id]), address, 'json')
        address_id = post_response.data["address_id"]
        response = self.client.delete(reverse(manage_specific_address, args = [user.id, address_id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
    
    def test_update_address(self):
        address = {
            "title": "Address9",
            "name": "OmerFaruk",
            "surname": "Deniz",
            "address": "Mahalle Sokak Sk. No 23/8",
            "province": "Sarıyer",
            "city": "Istanbul",
            "phone": {"country_code": "+90", "number": "5351234567"},
            "country": "Turkey",
            "zip_code": "34344"
        }
        post_response = self.client.post(reverse(manage_addresses, args = [user.id]), address, 'json')
        address_id = post_response.data["address_id"]
        response = self.client.put(reverse(manage_specific_address, args = [user.id, address_id]), address, 'json')
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)