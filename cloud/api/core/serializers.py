from rest_framework import serializers
from core.models import Coral, Species


class ImageSerializer(serializers.Serializer):
    """Serializer for the image upload"""
    image = serializers.ImageField()


class CoralSerializer(serializers.ModelSerializer):
    """Serializer for the coral models"""

    class Meta:
        model = Coral
        fields = ('id', 'coral_genus', 'coral_family', 'discoverer', 'year_discovered',
                  'characteristic', 'kind_of_look_alike', 'distribution', 'coral_species')
        read_only_fields = ('id',)


class SpeciesSerializer(serializers.ModelSerializer):
    """Serializer for the coral species"""

    class Meta:
        model = Species
        fields = ('id', 'name', 'corals')
        read_only_fields = ('id',)
