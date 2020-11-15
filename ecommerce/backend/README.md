
To setup your development environment:

1. Create virtual environment `python -m venv djangoenv`

2. Activate virtual environment `djangoenv\Scripts\activate`

3. Install all requirements `pip install -r requirements.txt`

4. "secrets.json" file will be provided, place it into folder containing manage.py

5. Handle database migrations if any `python manage.py migrate`

6. Run development server `python manage.py runserver`

