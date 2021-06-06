from rest_framework import serializers
from core.models import Coral


class ImageSerializer(serializers.Serializer):
    """Serializer for the image upload"""
    image = serializers.ImageField()


class CoralSerializer(serializers.ModelSerializer):
    """Serializer for the coral models"""

    class Meta:
        model = Coral
        fields = ('id', 'full_name', 'full_name_abbreviation', 'coral_genus', 'coral_family', 'discoverer', 'year_discovered',
                  'characteristic', 'kind_of_look_alike', 'distribution', 'coral_type', 'image_path')
        read_only_fields = ('id',)

class CoralPostSerializer(serializers.ModelSerializer):
    """Serializer for the coral models from /api/predict"""

    class Meta:
        model = Coral
        fields = ('id', 'full_name', 'image_path', 'coral_type')
        read_only_fields = ('id',)