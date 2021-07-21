from django.db import models
from .shared_mood import SharedMood
from .geometry_shape import GeometryShape


class SharedMoodGeometryShape(models.Model):

    index_in_list = models.IntegerField(
        help_text='Used for restore sparse list')
    color = models.IntegerField(help_text='Color in flutter 0xFF000000')
    shared_mood = models.ForeignKey(
        SharedMood, db_column='shared_mood_id', on_delete=models.CASCADE)
    geometry_shape = models.ForeignKey(
        GeometryShape, db_column='geometry_shape_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'shared_mood_geometry_shapes'
        unique_together = (('shared_mood', 'index_in_list', 'geometry_shape'),)
        app_label = 'app_moods'
