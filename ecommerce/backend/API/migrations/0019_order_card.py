# Generated by Django 3.1.3 on 2020-12-22 18:42

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0018_merge_20201221_1032'),
    ]

    operations = [
        migrations.AddField(
            model_name='order',
            name='card',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='API.card'),
        ),
    ]