# Generated by Django 3.1.3 on 2020-12-13 21:57

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0009_auto_20201212_1440'),
    ]

    operations = [
        migrations.AddField(
            model_name='product',
            name='rating',
            field=models.FloatField(default=0),
        ),
    ]
