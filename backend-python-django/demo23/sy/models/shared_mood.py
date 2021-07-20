from django.db import models


class SharedMood(models.Model):

    class Meta:
        db_table = 'shared_moods'
        app_label = 'sy'
