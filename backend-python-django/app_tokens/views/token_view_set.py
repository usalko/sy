from os import urandom
from threading import Lock
from random import seed
from random import getrandbits
from uuid import UUID
from datetime import datetime

from rest_framework import mixins, viewsets, permissions
from rest_framework.decorators import action
from rest_framework.response import Response

from common import AutoDocStringSchema
from app_tokens.models import Token
from app_tokens.serializers import TokenSerializer


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
                  'application/json': ""
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
            seed(int.from_bytes(int(user_agent_hash).to_bytes(4, 'big', signed = True) +
                                int(random_seed).to_bytes(4, 'big', signed = True), 'big'))
            token.id = UUID(bytes=(int.from_bytes(urandom(16), 'big') ^ getrandbits(128)).to_bytes(16, 'big'), version=4)
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
                  'application/json': true
        '''
        token = request.query_params['token']
        if not token:
            raise Exception(
                f'Invalid request token must be determined in query string')
        return Response(bool(Token.objects.filter(pk=token).exists()))
