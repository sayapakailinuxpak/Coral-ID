from django.db import models


class Coral(models.Model):
    """Coral Model"""
    full_name = models.CharField(max_length=255)
    coral_genus = models.CharField(max_length=255)
    coral_family = models.CharField(max_length=255)
    discoverer = models.CharField(max_length=255)
    year_discovered = models.IntegerField()
    characteristic = models.CharField(max_length=1024)
    kind_of_look_alike = models.CharField(max_length=1024)
    distribution = models.CharField(max_length=1024)
    image_path = models.CharField(max_length=255)

    coral_type = models.CharField(max_length=255)
    full_name_abbreviation = models.CharField(max_length=255)