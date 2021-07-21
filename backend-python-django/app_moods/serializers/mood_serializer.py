from app_moods.models import Mood
from rest_framework import serializers


class MoodSerializer(serializers.Serializer):

    class Meta:
        model = Mood
        fields = ['created']

