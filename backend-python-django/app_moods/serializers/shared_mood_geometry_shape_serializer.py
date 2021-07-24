# Sy (Share your mood with anyone)
# Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http:#www.gnu.org/licenses/>.

from collections.abc import Iterable, Mapping
from io import BytesIO

from app_moods.models import SharedMoodGeometryShape
from app_moods.serializers import GeometryShapeSerializer
from rest_framework import serializers
from rest_framework.parsers import JSONParser


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
