from django.db import models
from .own_mood import OwnMood
from .mood_geometry_shape import MoodGeometryShape


class OwnMoodGeometryShape(MoodGeometryShape):

    own_mood = models.ForeignKey(
        OwnMood, db_column='own_mood_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'own_mood_geometry_shapes'
        unique_together = (('own_mood', 'index_in_list', 'geometry_shape'),)
        app_label = 'app_moods'
