from typing import List

from rest_framework import serializers

from app_moods.models import Mood
from app_moods.models import MoodGeometryShape
from app_moods.serializers import GeometryShapeSerializer


class MoodSerializer(serializers.Serializer):

    created = serializers.DateTimeField()
    geometry_shape = GeometryShapeSerializer()

    def create(self, validated_data):
        return Mood(**validated_data)

    def to_representation_mood_geometry_shapes(self, mood_geometry_shapes: List[MoodGeometryShape]):
        i = 0
        result = []
        # Unpack list
        for mood_geometry_shape in sorted(mood_geometry_shapes, key=lambda x: x.index_in_list):
            if i < mood_geometry_shape.index_in_list:
                result.extend([None] * (mood_geometry_shape.index_in_list - i))
            result.append({
                'color': mood_geometry_shape.color,
                'geometry_shape': self.fields['geometry_shape'].to_representation(mood_geometry_shape.geometry_shape)
            })
            i = mood_geometry_shape.index_in_list + 1
        return result

    class Meta:
        model = Mood
        fields = '__all__'
