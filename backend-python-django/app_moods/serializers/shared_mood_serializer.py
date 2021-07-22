from rest_framework import serializers

from app_moods.models import SharedMood
from app_moods.serializers import MoodSerializer
from app_moods.serializers import SharedMoodGeometryShapesSerializer


class SharedMoodSerializer(serializers.HyperlinkedModelSerializer, MoodSerializer):

    mood_geometry_shapes = SharedMoodGeometryShapesSerializer()

    def create(self):
        shapes = self.validated_data.pop('mood_geometry_shapes')
        result = SharedMood(**self.validated_data)
        result.mood_geometry_shapes = shapes
        return result

    class Meta:
        model = SharedMood
        fields = '__all__'
