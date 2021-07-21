from django.db import models
from .mood import Mood


class SharedMood(Mood):

    class Meta:
        db_table = 'shared_moods'
        app_label = 'app_moods'
