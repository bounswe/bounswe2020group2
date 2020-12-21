"""
Django settings for getflix project.
Generated by 'django-admin startproject' using Django 3.1.3.
For more information on this file, see
https://docs.djangoproject.com/en/3.1/topics/settings/
For the full list of settings and their values, see
https://docs.djangoproject.com/en/3.1/ref/settings/
"""

from pathlib import Path

# Build paths inside the project like this: BASE_DIR / 'subdir'.
BASE_DIR = Path(__file__).resolve().parent.parent


import json
import os
from django.core.exceptions import ImproperlyConfigured

##
## DO NOT TOUCH THIS PART
## IF YOU NEED TO WORK LOCALLY 
## PASS THE ENVIRONMENT VARIABLES FROM COMMAND LINE
## 
secrets = {
    'HOST': os.environ.get('DB_HOST'),
    'USER': os.environ.get('DB_USER'),
    'PASSWORD': os.environ.get('DB_PASSWORD'),
    'EMAIL_BACKEND': os.environ.get('EMAIL_BACKEND'),
    'MAILER_EMAIL_BACKEND': os.environ.get('MAILER_EMAIL_BACKEND'),
    'EMAIL_HOST': os.environ.get('EMAIL_HOST'),
    'EMAIL_HOST_PASSWORD': os.environ.get('EMAIL_HOST_PASSWORD'),
    'EMAIL_HOST_USER': os.environ.get('EMAIL_HOST_USER'),
    'DEFAULT_FROM_EMAIL': os.environ.get('DEFAULT_FROM_EMAIL')
}

'''with open(os.path.join(BASE_DIR, 'secrets.json')) as secrets_file:
    secrets = json.load(secrets_file)'''

def get_debug():
    debug = os.environ.get("DEBUG")
    if debug is None:
        return True
    else:
        if debug == "False":
            return False
        return True

def get_secret(setting, secrets=secrets):
    """Get secret setting or fail with ImproperlyConfigured"""
    try:
        return secrets[setting]
    except KeyError:
        raise ImproperlyConfigured("Set the {} setting".format(setting))

EMAIL_BACKEND = get_secret('EMAIL_BACKEND')
MAILER_EMAIL_BACKEND = get_secret('MAILER_EMAIL_BACKEND')
EMAIL_HOST = get_secret('EMAIL_HOST')
EMAIL_HOST_PASSWORD = get_secret('EMAIL_HOST_PASSWORD')
EMAIL_HOST_USER = get_secret('EMAIL_HOST_USER')
EMAIL_PORT = 587  
EMAIL_USE_SSL = False
EMAIL_USE_TLS = True  
DEFAULT_FROM_EMAIL = get_secret('DEFAULT_FROM_EMAIL')

FRONTEND_URL = "ec2-18-222-139-223.us-east-2.compute.amazonaws.com"
# FRONTEND_URL = "localhost:5000"

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/3.1/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = 'wc$0ekguzl&$5gaotf)0!-#znaitq%5jafib)yh494s2a-a5ic'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = get_debug()

ALLOWED_HOSTS = ['*']

CORS_ALLOW_ALL_ORIGINS = True
CORS_ALLOW_CREDENTIALS = True

# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'corsheaders',
    'rest_framework',
    'API'
]

MIDDLEWARE = [
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': (
        'API.utils.authentication.JWTAuthentication',
    ),
    'DEFAULT_PERMISSION_CLASSES': (
        'rest_framework.permissions.AllowAny',
    )
}

ROOT_URLCONF = 'getflix.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': (os.path.join(os.path.dirname(__file__), '..', 'templates').replace('\\','/'),),
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'getflix.wsgi.application'


# Database
# https://docs.djangoproject.com/en/3.1/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'testdb',
        'USER': get_secret('USER'),
        'PASSWORD': get_secret('PASSWORD'),
        'HOST': get_secret("HOST"),
        'PORT': '5432',
    }
}


# Password validation
# https://docs.djangoproject.com/en/3.1/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# Internationalization
# https://docs.djangoproject.com/en/3.1/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True


# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/3.1/howto/static-files/

STATIC_URL = '/static/'