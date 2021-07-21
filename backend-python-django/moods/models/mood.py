from django.db import models


class Mood(models.Model):

    class Meta:
        abstract = True
        app_label = 'moods'

    def __str__(self):
        return self.name

    def get_absolute_url(self):
        return reverse("_detail", kwargs={"pk": self.pk})
