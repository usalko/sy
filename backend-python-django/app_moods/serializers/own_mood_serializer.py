from app_moods.models import OwnMood
from rest_framework import serializers


class OwnMoodSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = OwnMood
        fields = ['created', 'geometry_shape']
