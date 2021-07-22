from rest_framework import serializers

from app_moods.models import OwnMood
from app_moods.serializers import MoodSerializer
from app_moods.serializers import OwnMoodGeometryShapesSerializer


class OwnMoodSerializer(serializers.HyperlinkedModelSerializer, MoodSerializer):

    mood_geometry_shapes = OwnMoodGeometryShapesSerializer()

    def create(self):
        shapes = self.validated_data.pop('mood_geometry_shapes')
        result = OwnMood(**self.validated_data)
        result.mood_geometry_shapes = shapes
        return result

    class Meta:
        model = OwnMood
        fields = '__all__'
