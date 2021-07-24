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

from collections.abc import Mapping
from io import BytesIO

from app_moods.models import GeometryShape
from rest_framework import serializers
from rest_framework.parsers import JSONParser


class GeometryShapeSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return {'mnemonic': instance.mnemonic}

    def to_internal_value(self, data):
        if not isinstance(data, Mapping):
            mapped_data = JSONParser().parse(BytesIO(data))
        else:
            mapped_data = data

        result = GeometryShape(**mapped_data)
        return result
