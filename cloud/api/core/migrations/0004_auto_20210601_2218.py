# Generated by Django 3.2.3 on 2021-06-01 22:18

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('core', '0003_coral_coral_type'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='coral',
            name='coral_species',
        ),
        migrations.AddField(
            model_name='coral',
            name='full_name',
            field=models.CharField(default=None, max_length=255),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='coral',
            name='full_name_abbreviation',
            field=models.CharField(default=None, max_length=255),
            preserve_default=False,
        ),
        migrations.DeleteModel(
            name='Species',
        ),
    ]
