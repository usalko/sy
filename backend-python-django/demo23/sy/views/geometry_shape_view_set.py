from rest_framework import viewsets
from rest_framework import permissions
from demo23.sy.models import GeometryShape
from demo23.sy.serializers import GeometryShapeSerializer


class GeometryShapeViewSet(viewsets.ModelViewSet):

    """
       API endpoint that allows see all available geometry shapes.
       """
    queryset = GeometryShape.objects.all()
    serializer_class = GeometryShapeSerializer
    permission_classes = [permissions.AllowAny]
