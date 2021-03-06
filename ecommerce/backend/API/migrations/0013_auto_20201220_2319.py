# Generated by Django 3.1.3 on 2020-12-20 20:19

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('API', '0012_merge_20201218_2040'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='purchase',
            name='name',
        ),
        migrations.RemoveField(
            model_name='purchase',
            name='user',
        ),
        migrations.AddField(
            model_name='purchase',
            name='vendor',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='API.vendor'),
            preserve_default=False,
        ),
        migrations.CreateModel(
            name='Order',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='API.user')),
            ],
        ),
        migrations.AddField(
            model_name='purchase',
            name='order',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, to='API.order'),
            preserve_default=False,
        ),
    ]
