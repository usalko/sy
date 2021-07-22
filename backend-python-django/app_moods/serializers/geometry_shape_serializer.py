from rest_framework import serializers

from app_moods.models import GeometryShape


class GeometryShapeSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return instance.mnemonic

    def to_internal_value(self, data):
        result = GeometryShape(**data)
        # TODO replace by right id
        return result
