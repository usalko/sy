from rest_framework import viewsets
from rest_framework import permissions
from moods.models import GeometryShape
from moods.serializers import GeometryShapeSerializer


class GeometryShapeViewSet(viewsets.ModelViewSet):

    """
       API endpoint that allows see all available geometry shapes.
       """
    queryset = GeometryShape.objects.all()
    serializer_class = GeometryShapeSerializer
    permission_classes = [permissions.AllowAny]
