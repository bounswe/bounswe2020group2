# Generated by Django 3.1.3 on 2021-01-19 22:52

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0023_merge_20210119_1812'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='productlistitem',
            name='name',
        ),
    ]