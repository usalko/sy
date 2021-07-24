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

from collections.abc import Iterable
from datetime import datetime

from app_moods.models import GeometryShape
from app_moods.views.mood_view_set import MoodViewSet
from app_tokens.views.token_view_set import TokenViewSet
from django.test import TestCase
from rest_framework.renderers import JSONRenderer
from rest_framework.test import APIRequestFactory


class MoodViewSetTest(TestCase):

    token = None

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
        # Request new token
        request = APIRequestFactory().get(
            '?', data={'user-agent-hash': -1, 'seed': 0})
        tested_view = TokenViewSet.as_view({'get': 'list'})
        response = tested_view(request)
        cls.token = response.data

    def test_keep_mood_for_now(self):
        """Creating a Geometry object"""
        request = APIRequestFactory().post('?token=%s' % self.token, data=JSONRenderer().render({
            'created': datetime.now().isoformat(),
            'geometry_shape': {'mnemonic': 'triangle'},
            'mood_geometry_shapes': []
        }), content_type='application/json')
        tested_view = MoodViewSet.as_view({'post': 'keep_mood_for_now'})
        response = tested_view(request)
        self.assertEqual(response.status_code, 200,
                         msg=f'Output is: {response}')
        output_json = response.data
        self.assertIsNotNone(output_json)
