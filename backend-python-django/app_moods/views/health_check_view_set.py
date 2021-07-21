from rest_framework import mixins, viewsets, permissions
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
