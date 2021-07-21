from .mood import Mood


class OwnMood(Mood):

    class Meta:
        db_table = 'own_moods'
        app_label = 'moods'
