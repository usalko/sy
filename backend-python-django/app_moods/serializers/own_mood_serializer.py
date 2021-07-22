from io import BytesIO
from collections import OrderedDict
from collections.abc import Mapping
from rest_framework import serializers
from rest_framework.parsers import JSONParser
from rest_framework.settings import api_settings
from django.core.exceptions import ValidationError as DjangoValidationError
from rest_framework.exceptions import ValidationError
from rest_framework.fields import get_error_detail
from rest_framework.fields import set_value
from rest_framework.fields import (
    CreateOnlyDefault, CurrentUserDefault, SkipField, empty
)

from app_moods.models import OwnMood
from app_moods.serializers import GeometryShapeSerializer


class OwnMoodSerializer(serializers.HyperlinkedModelSerializer):

    created = serializers.DateTimeField()
    geometry_shape = GeometryShapeSerializer()

    def to_internal_value(self, data):
        """
        Dict of native values <- Dict of primitive datatypes.
        """
        if not isinstance(data, Mapping):
            mapped_data = JSONParser().parse(BytesIO(data))
        else:
            mapped_data = data

        ret = OrderedDict()
        errors = OrderedDict()
        fields = self._writable_fields

        for field in fields:
            validate_method = getattr(
                self, 'validate_' + field.field_name, None)
            primitive_value = field.get_value(mapped_data)
            try:
                validated_value = field.run_validation(primitive_value)
                if validate_method is not None:
                    validated_value = validate_method(validated_value)
            except ValidationError as exc:
                errors[field.field_name] = exc.detail
            except DjangoValidationError as exc:
                errors[field.field_name] = get_error_detail(exc)
            except SkipField:
                pass
            else:
                set_value(ret, field.source_attrs, validated_value)

        if errors:
            raise ValidationError(errors)

        return ret

    def create(self):
        return OwnMood(**self.validated_data)

    class Meta:
        model = OwnMood
        fields = ['created', 'geometry_shape']
