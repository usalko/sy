from app_moods.models import GeometryShape
from rest_framework import serializers


class GeometryShapeSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return instance.mnemonic

