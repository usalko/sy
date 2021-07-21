import uuid
from django.db import models


class Token(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    created = models.DateTimeField(help_text='Timestamp when token created')

    class Meta:
        db_table = 'tokens'
        app_label = 'app_tokens'
