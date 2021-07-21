from rest_framework import mixins, viewsets, permissions
from app_moods.models import GeometryShape
from app_moods.serializers import MoodSerializer
from rest_framework.decorators import action

class MoodViewSet(viewsets.GenericViewSet):
    """
      Mood requests.

    """
    permission_classes = [permissions.AllowAny]
    serializer_class = MoodSerializer
    #queryset = GeometryShape.objects.all().order_by('id')
    # from rest_framework.schemas import AutoSchema
    # import coreapi
    # import coreschema
    # schema = AutoSchema(
    #     manual_fields=[
    #         coreapi.Field(
    #             'id',
    #             required=False,
    #             location='path',
    #             description='A unique integer value identifying specific your-model-name.',
    #             schema=coreschema.Integer(),
    #         ),
    #     ]
    # )

    @action(detail=False, methods=['get'], url_path='GetHistory', url_name='Get top 50 history events in reverse order by creation date')
    def get_history(self, request):
        ...

    @action(detail=False, methods=['get'], url_path='GetSharedMoods', url_name='Request all shared moods')
    def get_shared_moods(self, request):
        ...

    @action(detail=False, methods=['post'], url_path='KeepMoodForNow', url_name='Keep your own mood for now')
    def keep_mood_for_now(self, request):
        ...

    @action(detail=False, methods=['post'], url_path='ShareMood', url_name='Share mood for anyone')
    def share_mood(self, request):
        ...
