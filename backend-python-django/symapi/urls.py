"""symapi URL Configuration

"""

from django.shortcuts import redirect
from django.urls import include, path
from rest_framework import routers
from app_swagger.views import schema_view
from app_moods.views import HealthCheckViewSet, MoodViewSet
from app_tokens.views import TokenViewSet
from django.views.generic import TemplateView

router = routers.DefaultRouter(trailing_slash=False)
router.register('Geometry', HealthCheckViewSet, basename='Geometry')
router.register('Token', TokenViewSet, basename='Token')
router.register('', MoodViewSet, basename='')


# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    path('', lambda request: redirect('swagger-ui/', permanent=False)),
    path('api/', include(router.urls)),
    path('v3/api-doc', schema_view, name='openapi-schema'),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    path('swagger-ui/', TemplateView.as_view(
        template_name='swagger-ui.html',
        extra_context={'schema_url':'openapi-schema'}
    ), name='swagger-ui'),
]