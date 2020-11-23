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
from API.views.shopping_cart import list_shopping_cart


urlpatterns = [
    path('admin', admin.site.urls),
    path('api', views.account.apiOverview, name="api"),
    path('regularsignup', views.account.register, name="register"),
    path('regularlogin', views.account.login, name="login"),
    path('init', views.account.init, name="init"),
    path('products/homepage/<int:no>', views.product.homepage_products),
    path('user/<int:id>/listShoppingCart', list_shopping_cart),
    path('user/<int:id>/shoppingCart', views.shopping_cart.add_shopping_cart_item),
    path('product/<int:productId>', views.product.product_detail)
]
