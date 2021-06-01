from django.db import models


class Coral(models.Model):
    """Coral Model"""
    coral_genus = models.CharField(max_length=255)
    coral_family = models.CharField(max_length=255)
    discoverer = models.CharField(max_length=255)
    year_discovered = models.IntegerField()
    characteristic = models.CharField(max_length=1024)
    kind_of_look_alike = models.CharField(max_length=1024)
    distribution = models.CharField(max_length=1024)
    coral_species = models.ForeignKey('Species', on_delete=models.CASCADE)
    coral_type = models.CharField(max_length=255)
    image_path = models.CharField(max_length=255)


class Species(models.Model):
    """Species of Corals Model"""
    name = models.CharField(max_length=255)
    corals = models.ManyToManyField('Coral')
