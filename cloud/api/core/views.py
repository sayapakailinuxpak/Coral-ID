from rest_framework import views, status
from rest_framework.response import Response
from django.core.files.storage import default_storage
from django.conf import settings
import uuid

from core import serializers, predictions


class call_model(views.APIView):
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

            file_name = default_storage.save(
                unique_filename, request.FILES['image'])
            file_url = default_storage.url(file_name)
            img_tensor = predictions.load_image_from_path(file_url)
            prediction = settings.H5_MODEL.predict(img_tensor)[0]
            default_storage.delete(file_name)

            payload = {'image': request.FILES['image'].name, 'prediction': {
                'dogs': prediction, 'cats': 1 - prediction}}
            return Response(payload, status=status.HTTP_200_OK)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
