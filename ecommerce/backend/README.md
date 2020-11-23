To setup your development environment:

1. Create virtual environment `python -m venv djangoenv`

2. Activate virtual environment with `djangoenv\Scripts\activate` in Windows, or with `source djangoenv/bin/activate` in Mac OS.

3. Install all requirements `pip install -r requirements.txt`

4. "secrets.json" file will be provided, place it into folder containing manage.py

5. Create migration files `python manage.py makemigrations`

6. Handle database migrations if any `python manage.py migrate`

7. Run development server `python manage.py runserver`

To setup your test database:

1. Install PostgreSQL and PgAdmin

2. In the PgAdmin panel, create a server with the info in the `secrets.json`

3. Create a database with name `testdb` in PgAdmin

4. Download the `dump.sql` file and run the following command `psql testdb < dump.sql` on your terminal if you are MacOS user or run `psql -U postgres -d testdb -f dump.sql` in the Windows Powershell

5. Make sure that name is written as `'NAME': 'testdb'` in the DATABASES variable in the `settings.py`, otherwise Django will not connect to the database