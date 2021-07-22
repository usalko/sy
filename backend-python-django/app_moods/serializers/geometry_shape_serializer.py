from io import BytesIO
from collections.abc import Mapping

from rest_framework import serializers
from rest_framework.parsers import JSONParser

from app_moods.models import GeometryShape


class GeometryShapeSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return instance.mnemonic

    def to_internal_value(self, data):
        if not isinstance(data, Mapping):
            mapped_data = JSONParser().parse(BytesIO(data))
        else:
            mapped_data = data

        result = GeometryShape(**mapped_data)
        return result
