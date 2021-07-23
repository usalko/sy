from rest_framework import mixins, viewsets, permissions

from common import AutoDocStringSchema

from app_moods.models import GeometryShape
from app_moods.serializers import GeometryShapeSerializer


class HealthCheckViewSet(mixins.ListModelMixin,
                         viewsets.GenericViewSet):
    """
      Health check base on retrive all geometry shapes.

    """
    permission_classes = [permissions.AllowAny]
    serializer_class = GeometryShapeSerializer
    queryset = GeometryShape.objects.all().order_by('id')

    schema = AutoDocStringSchema()

    def list(self, request, *args, **kwargs):
        '''
        get:
          description: Get all geometry shapes
          summary: All geometry shapes registered in the system
          responses:
            '200':
                description: List of geometry shapes
                content:
                  'application/json': ""
        '''
        response = super().list(request, *args, **kwargs)
        return response
