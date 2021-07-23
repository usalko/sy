from io import BytesIO
from datetime import datetime

from django.db import transaction

from rest_framework import status
from rest_framework import mixins
from rest_framework import viewsets
from rest_framework import permissions
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.fields import empty
from rest_framework.parsers import JSONParser

from app_moods.serializers import MoodSerializer
from app_moods.serializers import OwnMoodSerializer
from app_moods.serializers import SharedMoodSerializer

from common import AutoDocStringSchema
from app_tokens.models import Token
from app_moods.models import GeometryShape
from app_moods.models import TokenOwnMood
from app_moods.models import Mood
from app_moods.models import OwnMood
from app_moods.models import OwnMoodGeometryShape
from app_moods.models import SharedMood
from app_moods.models import SharedMoodGeometryShape


class MoodViewSet(viewsets.GenericViewSet):
    """
      Mood requests.

    """
    permission_classes = [permissions.AllowAny]
    serializer_class = MoodSerializer

    schema = AutoDocStringSchema()

    @classmethod
    def _check_token_and_throw_error_if_token_is_not_valid(cls, token: str):
        if not token:
            raise Exception(
                f'Invalid request token must be determined in query string')
        token_pk = str(token).strip('"')
        if len(token_pk) != 36:
            raise Exception(
                f'Invalid request token {token_pk} has wrong length')
        if not Token.objects.filter(pk=token_pk).exists():
            raise Exception(
                f'Token {token_pk} did not register')

    @action(detail=False, methods=['get'], url_path='GetHistory')
    def get_history(self, request):
        '''
        get:
          description: Get top 50 history events in reverse order by creation date
          summary: Request your own mood history
          parameters:
            - name: token
              in: query
              description: token
              required: true
              schema:
                  type: string
          responses:
            '200':
                description: List of your own moods
                content:
                  'application/json': {}
        '''
        token = request.query_params['token']
        self._check_token_and_throw_error_if_token_is_not_valid(token)
        serializer = OwnMoodSerializer([tom.own_mood for tom in TokenOwnMood.objects.filter(
            token=Token.of(token.strip('"'))).order_by('-id')[:50]], many=True, context={'request': request})
        return Response(serializer.data)

    @action(detail=False, methods=['get'], url_path='GetSharedMoods')
    def get_shared_moods(self, request):
        '''
        get:
          description: Get top 200 shared moods in reverse order by creation date
          summary: Request all shared moods
          parameters:
            - name: token
              in: query
              description: token
              required: true
              schema:
                  type: string
          responses:
            '200':
                description: List of shared moods
                content:
                  'application/json': {}
        '''
        #token = request.query_params.get('token')
        # self._check_token_and_throw_error_if_token_is_not_valid(token)
        serializer = SharedMoodSerializer(
            SharedMood.objects.all().order_by('-id')[:200], many=True, context={'request': request})
        return Response(serializer.data)

    @ action(detail=False, methods=['post'], url_path='KeepMoodForNow')
    def keep_mood_for_now(self, request):
        '''
        post:
          description: Save your own mood
          summary: Keep your own mood for now
          parameters:
            - name: token
              in: query
              description: token
              required: true
              schema:
                  type: string
          requestBody:
            description: your *own* mood
            required: true
            content:
              application/json:
                schema:
                  $ref: '#/components/schemas/Mood'
          responses:
            '200':
                description: OK
            '500':
                description: Error occur during the process request
        '''
        token = request.query_params.get('token')
        self._check_token_and_throw_error_if_token_is_not_valid(token)
        bodyData = JSONParser().parse(BytesIO(request.body))
        if not bodyData.get('created'):
            bodyData['created'] = datetime.now().isoformat()
        serializer = OwnMoodSerializer(data=bodyData)
        if not serializer.is_valid():
            return Response(serializer.error_messages, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        own_mood = serializer.create()
        self._prepare_mood(own_mood)
        with transaction.atomic():
            own_mood.save()
            for own_mood_geometry_shape in own_mood.mood_geometry_shapes:
                own_mood_geometry_shape.save()
            TokenOwnMood(token=Token(token), own_mood=own_mood).save()
        return Response('')

    @ action(detail=False, methods=['post'], url_path='ShareMood')
    def share_mood(self, request):
        '''
        post:
          description: Share your own mood
          summary: Share your mood with anyone
          parameters:
            - name: token
              in: query
              description: token
              required: true
              schema:
                  type: string
          requestBody:
            description: your mood for *share*
            required: true
            content:
              application/json:
                schema:
                  $ref: '#/components/schemas/Mood'
          responses:
            '200':
                description: OK
            '500':
                description: Error occur during the process request
        '''
        token = request.query_params.get('token')
        self._check_token_and_throw_error_if_token_is_not_valid(token)
        bodyData = JSONParser().parse(BytesIO(request.body))
        if not bodyData.get('created'):
            bodyData['created'] = datetime.now().isoformat()
        serializer = SharedMoodSerializer(data=bodyData)
        if not serializer.is_valid():
            return Response(serializer.error_messages, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
        shared_mood = serializer.create()
        self._prepare_mood(shared_mood)
        with transaction.atomic():
            shared_mood.save()
            for shared_mood_geometry_shape in shared_mood.mood_geometry_shapes:
                shared_mood_geometry_shape.save()
        return Response('')

    def _prepare_mood(self, mood: Mood):
        # Remove id
        mood.id = None
        # Restore geometry shapes
        shapes = {
            geometry_shape.mnemonic: geometry_shape.id for geometry_shape in GeometryShape.objects.all()}
        mood.geometry_shape.id = shapes[mood.geometry_shape.mnemonic]
        if mood.mood_geometry_shapes:
            for mood_geometry_shape in mood.mood_geometry_shapes:
                if mood_geometry_shape:
                    if isinstance(mood_geometry_shape, OwnMoodGeometryShape):
                        mood_geometry_shape.own_mood = mood
                    if isinstance(mood_geometry_shape, SharedMoodGeometryShape):
                        mood_geometry_shape.shared_mood = mood
                    mood_geometry_shape.geometry_shape.id = shapes[
                        mood_geometry_shape.geometry_shape.mnemonic]
