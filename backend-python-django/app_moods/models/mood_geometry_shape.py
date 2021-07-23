from django.db import models
from .geometry_shape import GeometryShape


class MoodGeometryShape(models.Model):

    index_in_list = models.IntegerField(
        help_text='Used for restore sparse list')
    color = models.IntegerField(help_text='Color in flutter 0xFF000000')
    geometry_shape = models.ForeignKey(
        GeometryShape, db_column='geometry_shape_id', on_delete=models.CASCADE)

    class Meta:
        abstract = True
        app_label = 'app_moods'
