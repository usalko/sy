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

from app_moods.models import GeometryShape
from app_moods.views.health_check_view_set import HealthCheckViewSet
from django.test import TestCase
from rest_framework.test import APIRequestFactory


class HealthCheckViewSetTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        """Quickly set up data for the whole TestCase"""
        GeometryShape(
            mnemonic='triangle',
        ).save()
        GeometryShape(
            mnemonic='square',
        ).save()
        GeometryShape(
            mnemonic='circle',
        ).save()

    def test_health(self):
        """/api/Geometry"""
        request = APIRequestFactory().get('')
        tested_view = HealthCheckViewSet.as_view({'get': 'list'})
        response = tested_view(request)
        output_json = response.data
        self.assertTrue(isinstance(output_json, Iterable))
        for shape_mnemonic in output_json:
            self.assertIsNotNone(shape_mnemonic)
            self.assertTrue(isinstance(shape_mnemonic, str))
        result = set(output_json)
        self.assertTrue('triangle' in result)
        self.assertTrue('square' in result)
        self.assertTrue('circle' in result)
