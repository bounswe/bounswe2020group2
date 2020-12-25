import json
from rest_framework.test import APIClient
from rest_framework import status
from django.test import TestCase, Client
from django.urls import reverse

from ..models import Product, Category, Subcategory, User, Vendor, Brand, ShoppingCartItem, Customer, Address, Purchase, Order, Card
from ..views.checkout import checkout_details, checkout_payment, checkout_cancel_order
from ..views.order import customer_order
from ..utils.crypto import Crypto
from ..utils import order_status

client = APIClient()
product_id_for_test = 1
product_deleted_id_for_test = 2
customer_id_for_test = 1
non_verified_id_for_test = 2
vendor_id_for_test = 3
address_id_for_test = 1
order_id_for_test = 1
card_id_for_test = 1

vendor_pk_for_test = 1

unit_price = 100
delivery_price = 7.9
amount = 2
discount = 0.1
crypto = Crypto()
p = None

class CheckoutTest(TestCase):
    def setUp(self):
        password = '12345678'
        salt = crypto.getSalt()
        password_hash = crypto.getHashedPassword(password, salt)

        user = User.objects.create(id=customer_id_for_test, username="customeruser", email="customeruser@gmail.com", role = 1, \
                                            password_salt=salt, password_hash=password_hash, is_verified=True)
        customer = Customer.objects.create(user = user, first_name="customerusername", last_name="customeruserlastname")
        address = Address.objects.create(id=address_id_for_test, user=user, title=f"Address-1", name="test", surname="test", 
                address= "Mahalle Sokak Sk. No 23/8", province= "Sarıyer", city= "Istanbul", 
                phone_country_code= "+90", phone_number= "5351234567", country= "Turkey", zip_code= "34344")
        
        c = Category.objects.create(id=4, name="Clothing")
        s = Subcategory.objects.create(id=10, category = c, name="Men's Fashion")
        b = Brand.objects.create(id=1, name="Mavi")
        u = User.objects.create(id=vendor_id_for_test, username="vendoruser", email="vendoruser@gmail.com", role = 2)
        v = Vendor.objects.create(id=vendor_pk_for_test, user = u, first_name="vendorname", last_name="vendorlastname")
        p = Product.objects.create(id=product_id_for_test, name = "Mavi T-shirt", price = unit_price, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, short_description="", long_description="",
            rating_count = 20, stock_amount = 10, subcategory = s, brand = b, vendor = v, discount = discount)
        Product.objects.create(id=product_deleted_id_for_test, name = "Mavi T-shirt", price = unit_price, 
            creation_date = "2019-08-20T07:22:34Z", total_rating = 4, short_description="", long_description="",
            rating_count = 20, stock_amount = 10, subcategory = s, brand = b, vendor = v, discount = discount, is_deleted = True)

        Card.objects.create(id=card_id_for_test, user_id=customer_id_for_test, name=f'Garanti{1}', owner_name="Customer User", 
            serial_number=((str(1)*4+'-')*4)[:-1], expiration_month=1, expiration_year=2020+1,cvv=111*1)

        ShoppingCartItem.objects.create(customer_id = customer_id_for_test, product_id=product_id_for_test, amount=amount)
        ShoppingCartItem.objects.create(customer_id = customer_id_for_test, product_id=product_deleted_id_for_test, amount=amount)

        non_verified_user = User.objects.create(id=non_verified_id_for_test, username="nonuser", email="nonuser@gmail.com", role = 1, \
                                            password_salt=salt, password_hash=password_hash, is_verified=False)
        non_verified_customer = Customer.objects.create(user=non_verified_user, first_name="nonusername", last_name="nonuserlastname")

        
    def login_credentials_settings(self):
        body = {
            'username': 'customeruser',
            'password': '12345678'
        }
        response = client.post(reverse('login'), body, 'json')
        token_customer = response.data["user"]["token"]
        return token_customer

    def non_login_credentials_settings(self):
        body = {
            'username': 'nonuser',
            'password': '12345678'
        }
        response = client.post(reverse('login'), body, 'json')
        token_customer = response.data["user"]["token"]
        return token_customer 
        

    def test_get_checkout_summary(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        response = client.get(reverse(checkout_details))

        self.assertEqual(response.status_code, 200)
        self.assertEqual('{:.2f}'.format(float(response.data["products_price"])), '{:.2f}'.format(unit_price * amount))
        self.assertEqual('{:.2f}'.format(float(response.data["delivery_price"])), '{:.2f}'.format(delivery_price))
        self.assertEqual('{:.2f}'.format(float(response.data["discount"])), '{:.2f}'.format(unit_price * amount * discount))
        self.assertEqual('{:.2f}'.format(float(response.data["total_price"])), '{:.2f}'.format(unit_price * amount * (1 - discount) + delivery_price))

    def test_check_non_verify(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.non_login_credentials_settings()))
        
        body = {"address_id": address_id_for_test}
        response = client.post(reverse(checkout_payment), body ,'json')

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], False)

    def test_post_checkout_payment(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        body = {"address_id": address_id_for_test, "card_id": card_id_for_test}
        response = client.post(reverse(checkout_payment), body ,'json')
        shop_cart = ShoppingCartItem.objects.filter(customer_id=customer_id_for_test)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(len(shop_cart), 0)

    def purchase(self):
        Order.objects.create(id=order_id_for_test, user_id=customer_id_for_test)
        Purchase.objects.create(product_id=product_id_for_test, amount=amount, unit_price=unit_price, status=order_status.OrderStatus.ACCEPTED.value,\
                                address_id=address_id_for_test, vendor_id=vendor_pk_for_test, order_id=order_id_for_test)
        Purchase.objects.create(product_id=product_id_for_test, amount=amount, unit_price=unit_price, status=order_status.OrderStatus.ACCEPTED.value,\
                                address_id=address_id_for_test, vendor_id=vendor_pk_for_test, order_id=order_id_for_test)

    def test_post_checkout_cancel_order(self):
        self.purchase()
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        response = client.post(reverse(checkout_cancel_order, args = [order_id_for_test]))
        purchase = Purchase.objects.filter(order_id=order_id_for_test).first()

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(purchase.status, order_status.OrderStatus.CANCELLED.value)

    def test_check_deleted_product(self):
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        body = {"address_id": address_id_for_test, "card_id": card_id_for_test}
        response = client.post(reverse(checkout_payment), body ,'json')
        order = Order.objects.filter(user_id=customer_id_for_test).first()
        purchase = Purchase.objects.filter(order_id=order.pk)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(len(purchase), 1)
        self.assertEqual(order.card.pk, card_id_for_test)

    def test_customer_order(self):
        self.purchase()
        client.credentials(HTTP_AUTHORIZATION='Bearer {}'.format(self.login_credentials_settings()))
        
        response = client.get(reverse(customer_order))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["status"]["successful"], True)
        self.assertEqual(int(response.data["orders"][0]["order_id"]), order_id_for_test)