# Generated by Django 3.1.3 on 2020-12-19 11:46

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0013_image'),
    ]

    operations = [
        migrations.AlterField(
            model_name='product',
            name='price',
            field=models.FloatField(),
        ),
    ]