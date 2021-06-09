import os
import json
import uuid
import operator
import subprocess

from rest_framework import views, status, viewsets, mixins
from rest_framework.response import Response
from django.core.files.storage import default_storage
from django.conf import settings
from django.core.serializers import json as jsonDjango

from core import serializers, predictions
from core.models import Coral


def exponential_to_int(value):
    return float(str(value).replace(',', '.'))


class CallModel(views.APIView):
    """Call the machine learning model"""
    serializer_class = serializers.ImageSerializer

    def post(self, request, pk=None):
        """Test for returning json request"""
        serializer = serializers.ImageSerializer(data=request.FILES)

        if serializer.is_valid():
            if len(request.FILES['image'].name.split('.')) != 2:
                return Response({'message': 'Invalid File Extension'}, status=status.HTTP_400_BAD_REQUEST)

            extension = request.FILES['image'].name.split('.')[1]
            unique_filename = str(uuid.uuid4()) + f'.{extension}'
            calculated_predictions = []
            coral_objects = []

            if os.getenv("PROD_SERVER"):
                path = f"/media/{unique_filename}"
            else:
                path = f"./{unique_filename}"
            file_name = default_storage.save(path, request.FILES['image'])
            file_url = default_storage.url(file_name)
            img_tensor = predictions.load_image_from_path(file_url)
            predictions_corals = settings.H5_MODEL.predict(img_tensor)[0]
            default_storage.delete(file_name)

            for prediction in predictions_corals:
                prediction = list(prediction)[-7:]
                coral_predictions = {
                    'ACER': exponential_to_int(prediction[0]), 'APAL': exponential_to_int(prediction[1]),
                    'CNAT': exponential_to_int(prediction[2]), 'MALC': exponential_to_int(prediction[3]),
                    'MCAV': exponential_to_int(prediction[4]), 'MMEA': exponential_to_int(prediction[5]),
                    'SSID': exponential_to_int(prediction[6]),
                }
                calculated_prediction = max(
                    coral_predictions.items(), key=operator.itemgetter(1))[0]
                if calculated_prediction not in calculated_predictions:
                    query_set = Coral.objects.filter(
                        full_name_abbreviation=calculated_prediction)
                    coral_object = serializers.CoralPostSerializer(
                        query_set, many=True)
                    calculated_predictions.append(calculated_prediction)
                    coral_objects.append(coral_object.data[0])

            payload = {'image': request.FILES['image'].name,
                       'calculated_prediction': calculated_predictions, 'prediction_corals': coral_objects}
            return Response(payload, status=status.HTTP_200_OK)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class CoralViewSet(viewsets.GenericViewSet,
                   mixins.ListModelMixin,
                   mixins.RetrieveModelMixin):
    # class CoralViewSet(viewsets.ModelViewSet):
    """List and Retrieve Coral Model from database"""
    queryset = Coral.objects.all()
    serializer_class = serializers.CoralSerializer
