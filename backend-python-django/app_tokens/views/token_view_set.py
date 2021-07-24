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

from datetime import datetime
from os import urandom
from random import getrandbits, seed
from threading import Lock
from uuid import UUID

from app_tokens.models import Token
from app_tokens.serializers import TokenSerializer
from common import AutoDocStringSchema
from rest_framework import mixins, permissions, viewsets
from rest_framework.decorators import action
from rest_framework.response import Response

RANDOM_SEED_LOCK_TIMEOUT_IN_SECONDS = 1


class TokenViewSet(mixins.ListModelMixin,
                   viewsets.GenericViewSet):
    '''
        Request and check tokens
    '''
    permission_classes = [permissions.AllowAny]
    serializer_class = TokenSerializer

    schema = AutoDocStringSchema()
    __lock = Lock()

    def list(self, request, *args, **kwargs):
        '''
        get:
          description: Request new token
          summary: Create new token by two hashes
          parameters:
            - name: user-agent-hash
              in: query
              description: Hash code of user agent string.
              required: true
              schema:
                  type: integer
            - name: seed
              in: query
              description: Random number.
              required: true
              schema:
                  type: integer
          responses:
            '200':
                description: Token as string
                content:
                  'application/json': []
        '''
        user_agent_hash = request.query_params['user-agent-hash']
        random_seed = request.query_params['seed']
        if not user_agent_hash:
            raise Exception(
                f'Invalid request user-agent-hash must be determined in query string')
        if not random_seed:
            raise Exception(
                f'Invalid request random random-seed must be determined in query string')
        token = Token()

        self.__lock.acquire(timeout=RANDOM_SEED_LOCK_TIMEOUT_IN_SECONDS)
        try:
            seed(int.from_bytes(int(user_agent_hash).to_bytes(4, 'big', signed=True) +
                                int(random_seed).to_bytes(4, 'big', signed=True), 'big'))
            token.id = UUID(bytes=(int.from_bytes(urandom(16), 'big') ^ getrandbits(
                128)).to_bytes(16, 'big'), version=4)
            token.created = datetime.now()  # Use local datetime
        finally:
            self.__lock.release()

        token.save()

        return Response(str(token.id))

    @action(detail=False, methods=['get'], url_path='Validation')
    def validation(self, request):
        '''
        get:
          description: Check that token is registered
          summary: Check if token is exist in database
          parameters:
            - name: token
              in: query
              description: String value as uuid 430d5a05-78d3-3f2c-9a9c-397db64b66c5.
              required: true
              schema:
                  type: string
          responses:
            '200':
                description: Boolean value
                content:
                  'application/json': []
        '''
        token = request.query_params['token']
        if not token:
            raise Exception(
                f'Invalid request token must be determined in query string')
        return Response(bool(Token.objects.filter(pk=token).exists()))
