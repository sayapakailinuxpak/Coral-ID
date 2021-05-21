from rest_framework import serializers


class ImageSerializer(serializers.Serializer):
    """Serializer for the image upload"""
    image = serializers.ImageField()
