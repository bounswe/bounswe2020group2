from rest_framework.test import APIClient
from django.test import TestCase
from rest_framework import status
from rest_framework.renderers import JSONRenderer
from django.urls import reverse
from django.test import tag,Client
from ..models import *


class TestVendorDetails(TestCase):
    def setUp(self):
        self.client = APIClient()
        u = User.objects.create(id=1, username="brkctn", email="brk@gmail.com", role = 2)
        v = Vendor.objects.create(id=1, user = u, first_name="brk", last_name="ctn",rating_count=10,total_rating=3,title="BRKTITLE")
        u = User.objects.create(id=2, username="burak", email="burak@coolmail.com", role = 2)
        v = Vendor.objects.create(id=2, user = u, first_name="burak", last_name="cetin",rating_count=10,total_rating=3,title="BURAKTITLE")
    @tag('vendor')
    def test_vendor_details1(self):
        response = self.client.get(reverse('vendor_details', args = [1]))
        self.assertEqual(response.data["title"],"BRKTITLE")
    @tag('vendor')
    def test_vendor_details2(self):
        response = self.client.get(reverse('vendor_details', args = [2]))
        self.assertEqual(response.data["title"],"BURAKTITLE")