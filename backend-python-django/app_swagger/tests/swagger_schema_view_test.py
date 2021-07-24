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

from app_swagger.views.swagger_schema_view import SwaggerSchemaView
from django.test import TestCase
from rest_framework.test import APIRequestFactory


class SwaggerSchemaViewTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        """Quickly set up data for the whole TestCase"""
        pass

    def test_request_schema(self):
        """Request openapi schema"""
        request = APIRequestFactory().get("")
        tested_view = SwaggerSchemaView.as_view()
        response = tested_view(request)
        output_json = response.data
        self.assertTrue(isinstance(output_json, Mapping))
        self.assertIsNotNone(output_json.get('paths'))
        self.assertIsNotNone(output_json.get('paths').get('/api/Geometry'))
        self.assertIsNotNone(output_json.get('paths').get('/api/Token'))
        self.assertIsNotNone(output_json.get(
            'paths').get('/api/Token/Validation'))
        self.assertIsNotNone(output_json.get('paths').get('/api/GetHistory'))
        self.assertIsNotNone(output_json.get(
            'paths').get('/api/GetSharedMoods'))
        self.assertIsNotNone(output_json.get(
            'paths').get('/api/KeepMoodForNow'))
        self.assertIsNotNone(output_json.get('paths').get('/api/ShareMood'))
        self.assertEqual(response.status_code, 200)
