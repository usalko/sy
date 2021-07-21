from django.db import models
from .geometry_shape import GeometryShape


class Mood(models.Model):

    created = models.DateTimeField(
        help_text='Timestamp when shared mood created')
    geometry_shape = models.ForeignKey(
        GeometryShape, db_column='geometry_shape_id', on_delete=models.CASCADE)

    class Meta:
        abstract = True
        app_label = 'app_moods'
