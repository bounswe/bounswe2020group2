# Generated by Django 3.1.3 on 2020-12-19 12:00

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0014_auto_20201219_1446'),
    ]

    operations = [
        migrations.AlterField(
            model_name='product',
            name='discount',
            field=models.FloatField(default=0),
        ),
    ]
