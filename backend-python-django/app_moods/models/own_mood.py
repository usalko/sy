from django.db import models
from .mood import Mood


class OwnMood(Mood):

    class Meta:
        db_table = 'own_moods'
        app_label = 'app_moods'
