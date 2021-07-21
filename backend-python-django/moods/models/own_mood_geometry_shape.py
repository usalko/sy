from django.db import models
from .own_mood import OwnMood
from .geometry_shape import GeometryShape


class OwnMoodGeometryShape(models.Model):

    index_in_list = models.IntegerField(
        help_text='Used for restore sparse list')
    color = models.IntegerField(help_text='Color in flutter 0xFF000000')
    own_mood = models.ForeignKey(
        OwnMood, db_column='own_mood_id', on_delete=models.CASCADE)
    geometry_shape = models.ForeignKey(
        GeometryShape, db_column='geometry_shape_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'own_mood_geometry_shapes'
        unique_together = (('own_mood', 'index_in_list', 'geometry_shape'),)
        app_label = 'moods'
