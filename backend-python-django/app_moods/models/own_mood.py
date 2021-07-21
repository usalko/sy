from django.db import models
from .mood import Mood
from .geometry_shape import GeometryShape


class OwnMood(Mood):

    created = models.DateTimeField(
        help_text='Timestamp when own mood created')
    geometry_shape = models.ForeignKey(
        GeometryShape, db_column='geometry_shape_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'own_moods'
        app_label = 'app_moods'
