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

from rest_framework import exceptions
from rest_framework.permissions import AllowAny
from rest_framework.renderers import CoreJSONRenderer, OpenAPIRenderer
from rest_framework.response import Response
from rest_framework.schemas.openapi import SchemaGenerator
from rest_framework.views import APIView


class SwaggerSchemaView(APIView):
    _ignore_model_permissions = True
    exclude_from_schema = True
    permission_classes = [AllowAny]
    renderer_classes = [
        CoreJSONRenderer,
        OpenAPIRenderer,
    ]

    def get(self, request):
        generator = SchemaGenerator(
            title='API Documentation',
            url=None,
            patterns=None,
            urlconf=None
        )
        schema = generator.get_schema(request=request)

        if not schema:
            raise exceptions.ValidationError(
                'The schema generator did not return a schema Document'
            )

        return Response(schema)
