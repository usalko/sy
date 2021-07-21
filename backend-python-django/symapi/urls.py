"""symapi URL Configuration

"""

from django.shortcuts import redirect
from django.urls import include, path
from rest_framework import routers
from app_swagger.views import schema_view
from app_moods.views import GeometryShapeViewSet

router = routers.DefaultRouter(trailing_slash=False)
router.register('Geometry', GeometryShapeViewSet)

# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    path('', lambda request: redirect('api/doc', permanent=False)),
    path('api/', include(router.urls)),
    path('api/doc', schema_view),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework'))
]