from app_moods.models import SharedMood
from rest_framework import serializers


class SharedMoodSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = SharedMood
        fields = ['created', 'geometry_shape']
