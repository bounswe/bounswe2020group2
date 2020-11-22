
To setup your development environment:

1. Create virtual environment `python -m venv djangoenv`

2. Activate virtual environment with `djangoenv\Scripts\activate` in Windows, or with `source djangoenv/bin/activate` in Mac OS.

3. Install all requirements `pip install -r requirements.txt`

4. "secrets.json" file will be provided, place it into folder containing manage.py

5. Create migration files `python manage.py makemigrations`

6. Handle database migrations if any `python manage.py migrate`

7. Run development server `python manage.py runserver`

