from rest_framework_swagger.views import get_swagger_view
from .geometry_shape_view_set import *

schema_view = get_swagger_view(title='API Documentation')
