from rest_framework import serializers


class TokenSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return instance.id
