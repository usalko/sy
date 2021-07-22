# Generated by Django 3.2.5 on 2021-07-21 15:13

from django.db import migrations, models
import uuid


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Token',
            fields=[
                ('id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('created', models.DateTimeField(help_text='Timestamp when token created')),
            ],
            options={
                'db_table': 'tokens',
            },
        ),
    ]