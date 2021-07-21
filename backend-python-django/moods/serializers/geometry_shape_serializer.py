from moods.models import GeometryShape
from rest_framework import serializers


class GeometryShapeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = GeometryShape
        fields = ['mnemonic']
