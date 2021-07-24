from django.db import models


class GeometryShape(models.Model):

    mnemonic = models.CharField(
        max_length=63, help_text='Unique name for geometric shape')

    class Meta:
        db_table = 'geometry_shapes'
        constraints = [
            models.UniqueConstraint(
                fields=['mnemonic'], name='unique shame name')
        ]
        app_label = 'app_moods'
