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

from app_moods.models import SharedMood
from app_moods.serializers import (MoodSerializer,
                                   SharedMoodGeometryShapesSerializer)
from rest_framework import serializers


class SharedMoodSerializer(serializers.HyperlinkedModelSerializer, MoodSerializer):

    mood_geometry_shapes = SharedMoodGeometryShapesSerializer()

    def to_representation(self, instance):
        result = {
            'created': instance.created.isoformat(),
            'geometry_shape': self.fields['geometry_shape'].to_representation(instance.geometry_shape),
            'mood_geometry_shapes': self.to_representation_mood_geometry_shapes(instance.sharedmoodgeometryshape_set.all())
        }
        return result

    def create(self):
        shapes = self.validated_data.pop('mood_geometry_shapes')
        result = SharedMood(**self.validated_data)
        result.mood_geometry_shapes = shapes
        return result

    class Meta:
        model = SharedMood
        fields = '__all__'
