"""getflix URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from API import views

urlpatterns = [
    path('admin', admin.site.urls),
    path('api', views.account.apiOverview, name="api"),
    path('regularsignup', views.account.register, name="register"),
    path('regularlogin', views.account.login, name="login"),
    path('init', views.account.init, name="init"),
    path('products/homepage/<int:num>', views.product.get_homepage_products),
    path('product/<int:product_id>', views.product.get_product_detail),
    path('categories', views.category.get_categories),
    path('search/products', views.search.products, name="search_products"),
    path('search/vendors', views.search.vendors, name="search_vendors"),
    path('search/brands', views.search.brands, name="search_brands"),
    path('customer/<int:customer_id>/shoppingcart/<int:sc_item_id>', views.shopping_cart.manage_specific_shopping_cart_item),
    path('customer/<int:customer_id>/shoppingcart', views.shopping_cart.manage_shopping_cart_items),
    path('customer/<int:customer_id>/addresses/<int:address_id>', views.address.manage_specific_address),
    path('customer/<int:customer_id>/addresses', views.address.manage_addresses),
    path('customer/<int:customer_id>/cards/<int:card_id>', views.card.manage_specific_card),
    path('customer/<int:customer_id>/cards', views.card.manage_cards),
    path('checkout/details', views.checkout.checkout_details),
    path('review', views.review.manage_review, name="review"),
    path('user/verify', views.verify.user_verify),
    path('email-verify/<uidb64>', views.verify.email_verify),
    path('image/<int:image_id>', views.product.get_image),
    path('vendor/product', views.product.vendor_product, name="vendor_product"),
    path('vendor/signup', views.account.vendor_register, name="vendor_signup"),
    path('vendor/order', views.order.vendor_orders,name="vendor_orders"),
    path('vendor/<int:vendor_id>/details', views.vendor.vendor_details,name="vendor_details"),
    path('checkout/payment', views.checkout.checkout_payment),
    path('checkout/cancelorder/<int:id>', views.checkout.checkout_cancel_order),
    path('customer/orders', views.order.customer_order),
    path('messages', views.message.manage_messages),
    path('notifications', views.notification.manage_notifications, name="notifications"),
    path('lists', views.product_list.product_list_create),
    path('recommendation', views.recommendation.recommend_products)
]
