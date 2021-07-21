from rest_framework import mixins, viewsets, permissions
from app_tokens.models import Token
from app_tokens.serializers import TokenSerializer
from rest_framework.decorators import action

class TokenViewSet(mixins.ListModelMixin,
                   viewsets.GenericViewSet):
    """
      Create and validate tokens.

    """
    permission_classes = [permissions.AllowAny]
    serializer_class = TokenSerializer

    # @action(detail=False, methods=['get'], url_path='/', url_name='Generate new token')
    # def get(self, request):
    #     ...

    @action(detail=False, methods=['get'], url_path='Validation', url_name='Check token')
    def validation(self, request):
        ...
