from uuid import uuid4
from django.db import models


class Token(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid4, editable=False)
    created = models.DateTimeField(help_text='Timestamp when token created')

    class Meta:
        db_table = 'tokens'
        app_label = 'app_tokens'
