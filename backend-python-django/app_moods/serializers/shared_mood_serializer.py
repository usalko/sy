from rest_framework import serializers

from app_moods.models import SharedMood
from app_moods.serializers import MoodSerializer
from app_moods.serializers import SharedMoodGeometryShapesSerializer


class SharedMoodSerializer(serializers.HyperlinkedModelSerializer, MoodSerializer):

    mood_geometry_shapes = SharedMoodGeometryShapesSerializer()

    def to_representation(self, instance):
        result = {
            'created': instance.created.isoformat(),
            'geometry_shape': self.fields['geometry_shape'].to_representation(instance.geometry_shape),
            'mood_geometry_shapes': []
        }
        return result

    def create(self):
        shapes = self.validated_data.pop('mood_geometry_shapes')
        result = SharedMood(**self.validated_data)
        result.mood_geometry_shapes = shapes
        return result

    class Meta:
        model = SharedMood
        fields = '__all__'
