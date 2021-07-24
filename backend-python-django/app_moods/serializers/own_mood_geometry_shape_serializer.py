from io import BytesIO
from collections.abc import Mapping
from collections.abc import Iterable

from rest_framework import serializers
from rest_framework.parsers import JSONParser

from app_moods.models import OwnMoodGeometryShape
from app_moods.serializers import GeometryShapeSerializer


class OwnMoodGeometryShapeSerializer(serializers.HyperlinkedModelSerializer):

    index_in_list = serializers.IntegerField()
    color = serializers.IntegerField()
    geometry_shape = GeometryShapeSerializer()

    def create(self):
        return OwnMoodGeometryShape(**self.validated_data)

    class Meta:
        model = OwnMoodGeometryShape
        fields = ['index_in_list', 'color', 'geometry_shape']


class OwnMoodGeometryShapesSerializer(serializers.Serializer):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.item_serializer_type = OwnMoodGeometryShapeSerializer

    def to_representation(self, instance):
        raise NotImplementedError()

    def to_internal_value(self, data):
        if isinstance(data, bytes):
            mapped_data = JSONParser().parse(BytesIO(data))
        else:
            mapped_data = data

        return [self._create_item(data_item, index) for index, data_item in enumerate(data) if not(data_item is None)]

    def _create_item(self, data: Mapping, index: int):
        data['index_in_list'] = index
        serializer = self.item_serializer_type(data=data)
        if not serializer.is_valid():
            raise Exception(f'Invalid geometry shape in content: {data}')
        return serializer.create()
