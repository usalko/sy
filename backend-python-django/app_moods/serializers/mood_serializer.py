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

from typing import List

from app_moods.models import Mood, MoodGeometryShape
from app_moods.serializers import GeometryShapeSerializer
from rest_framework import serializers


class MoodSerializer(serializers.Serializer):

    created = serializers.DateTimeField()
    geometry_shape = GeometryShapeSerializer()

    def create(self, validated_data):
        return Mood(**validated_data)

    def to_representation_mood_geometry_shapes(self, mood_geometry_shapes: List[MoodGeometryShape]):
        i = 0
        result = []
        # Unpack list
        for mood_geometry_shape in sorted(mood_geometry_shapes, key=lambda x: x.index_in_list):
            if i < mood_geometry_shape.index_in_list:
                result.extend([None] * (mood_geometry_shape.index_in_list - i))
            result.append({
                'color': mood_geometry_shape.color,
                'geometry_shape': self.fields['geometry_shape'].to_representation(mood_geometry_shape.geometry_shape)
            })
            i = mood_geometry_shape.index_in_list + 1
        return result

    class Meta:
        model = Mood
        fields = '__all__'
