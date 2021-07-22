from io import BytesIO
from collections.abc import Mapping
from collections.abc import Iterable

from rest_framework import serializers
from rest_framework.parsers import JSONParser

from app_moods.models import SharedMoodGeometryShape
from app_moods.serializers import GeometryShapeSerializer


class SharedMoodGeometryShapeSerializer(serializers.HyperlinkedModelSerializer):

    index_in_list = serializers.IntegerField()
    color = serializers.IntegerField()
    geometry_shape = GeometryShapeSerializer()

    def create(self):
        return SharedMoodGeometryShape(**self.validated_data)

    class Meta:
        model = SharedMoodGeometryShape
        fields = ['index_in_list', 'color', 'geometry_shape']


class SharedMoodGeometryShapesSerializer(serializers.Serializer):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.item_serializer_type = SharedMoodGeometryShapeSerializer

    def to_representation(self, instance):
        return []

    def to_internal_value(self, data):
        if isinstance(data, bytes):
            mapped_data = JSONParser().parse(BytesIO(data))
        else:
            mapped_data = data

        if isinstance(data, Iterable):
            return [self._create_item(data_item, index) for index, data_item in enumerate(data) if not(data_item is None)]

        return []

    def _create_item(self, data: Mapping, index: int):
        data['index_in_list'] = index
        serializer = self.item_serializer_type(data=data)
        if not serializer.is_valid():
            raise Exception(f'Invalid geometry shape in content: {data}')
        return serializer.create()
