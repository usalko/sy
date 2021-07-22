from rest_framework import serializers

from app_moods.models import Mood
from app_moods.serializers import GeometryShapeSerializer


class MoodSerializer(serializers.Serializer):

    created = serializers.DateTimeField()
    geometry_shape = GeometryShapeSerializer()

    def create(self, validated_data):
        return Mood(**validated_data)

    def update(self, instance, validated_data):
        instance.created = validated_data.get('created', instance.created)
        return instance

    class Meta:
        model = Mood
        fields = '__all__'
