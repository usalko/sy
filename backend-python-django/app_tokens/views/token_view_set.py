from rest_framework import mixins, viewsets, permissions
from app_tokens.models import Token
from app_tokens.serializers import TokenSerializer
from rest_framework.decorators import action
from rest_framework.response import Response
from common import AutoDocStringSchema


class TokenViewSet(mixins.ListModelMixin,
                   viewsets.GenericViewSet):
    '''
        Request and check tokens
    '''
    permission_classes = [permissions.AllowAny]
    serializer_class = TokenSerializer

    schema = AutoDocStringSchema()

    def list(self, request, *args, **kwargs):
        '''
        get:
          description: Generate new token
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
                f'Invalid request random user-agent-hash must be determined in query string')
        if not random_seed:
            raise Exception(
                f'Invalid request random random-seed must be determined in query string')
        return Response('Ok')

    @action(detail=False, methods=['get'], url_path='Validation')
    def validation(self, request):
        '''
        get:
          description: Check that token is registered
          summary: Check if token is exist in database
        '''
        ...
