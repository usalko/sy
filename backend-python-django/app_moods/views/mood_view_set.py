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

    @action(detail=False, methods=['get'], url_path='GetHistory')
    def get_history(self, request):
        """Get top 50 history events in reverse order by creation date"""
        ...

    @action(detail=False, methods=['get'], url_path='GetSharedMoods')
    def get_shared_moods(self, request):
        """Request all shared moods"""
        ...

    @action(detail=False, methods=['post'], url_path='KeepMoodForNow')
    def keep_mood_for_now(self, request):
        """Keep your own mood for now"""
        ...

    @action(detail=False, methods=['post'], url_path='ShareMood')
    def share_mood(self, request):
        """Share mood for anyone"""
        ...
