from app_moods.models import Mood
from rest_framework import serializers


class MoodSerializer(serializers.Serializer):

    created = serializers.DateTimeField()

    def run_validation(self, data=None):
        super().run_validation(data)

    def create(self, validated_data):
        return Mood(**validated_data)

    def update(self, instance, validated_data):
        instance.created = validated_data.get('created', instance.created)
        return instance

    class Meta:
        model = Mood
        fields = ['created', 'geometry_shape']
