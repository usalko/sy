from django.db import models


class OwnMood(models.Model):

    class Meta:
        db_table = 'own_moods'
        app_label = 'sy'
