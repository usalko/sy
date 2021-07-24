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

from app_moods.models import GeometryShape
from app_moods.serializers import GeometryShapeSerializer
from common import AutoDocStringSchema
from rest_framework import mixins, permissions, viewsets


class HealthCheckViewSet(mixins.ListModelMixin,
                         viewsets.GenericViewSet):
    """
      Health check base on retrive all geometry shapes.

    """
    permission_classes = [permissions.AllowAny]
    serializer_class = GeometryShapeSerializer
    queryset = GeometryShape.objects.all().order_by('id')

    schema = AutoDocStringSchema()

    def list(self, request, *args, **kwargs):
        '''
        get:
          description: Get all geometry shapes
          summary: All geometry shapes registered in the system
          responses:
            '200':
                description: List of geometry shapes
                content:
                  'application/json': []
        '''
        response = super().list(request, *args, **kwargs)
        response.data = [geometry_shape['mnemonic'] for geometry_shape in response.data]
        return response
