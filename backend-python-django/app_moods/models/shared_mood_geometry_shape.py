from django.db import models
from .shared_mood import SharedMood
from .mood_geometry_shape import MoodGeometryShape


class SharedMoodGeometryShape(MoodGeometryShape):

    shared_mood = models.ForeignKey(
        SharedMood, db_column='shared_mood_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'shared_mood_geometry_shapes'
        unique_together = (('shared_mood', 'index_in_list', 'geometry_shape'),)
        app_label = 'app_moods'
