from rest_framework import viewsets
from rest_framework import permissions
from app_moods.models import GeometryShape
from app_moods.serializers import GeometryShapeSerializer


class GeometryShapeViewSet(viewsets.ReadOnlyModelViewSet):

    """
       API endpoint that allows see all available geometry shapes.
       """
    queryset = GeometryShape.objects.all().order_by('id')
    serializer_class = GeometryShapeSerializer
    permission_classes = [permissions.AllowAny]
