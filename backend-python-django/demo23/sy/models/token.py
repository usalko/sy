import uuid
from django.db import models


class Token(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    # created = models

    class Meta:
        db_table = 'token'
        app_label = 'sy'
