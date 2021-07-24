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

from os import environ
from uuid import UUID

from app_tokens.views.token_view_set import TokenViewSet
from django.test import TestCase, runner
from rest_framework.test import APIRequestFactory


class TokenViewSetTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        """Quickly set up data for the whole TestCase"""
        pass

    def test_request_token(self):
        """/api/Token"""
        request = APIRequestFactory().get(
            '?', data={'user-agent-hash': -1, 'seed': 0})
        tested_view = TokenViewSet.as_view({'get': 'list'})
        response = tested_view(request)
        token = response.data
        self.assertEqual(response.status_code, 200)
        self.assertTrue(UUID(token, version=4))
        return token

    def test_token_validation(self):
        """/api/Token/Validation"""
        token = self.test_request_token()
        request = APIRequestFactory().get('?', data={'token': token})
        tested_view = TokenViewSet.as_view({'get': 'validation'})
        response = tested_view(request)
        self.assertEqual(response.status_code, 200)
