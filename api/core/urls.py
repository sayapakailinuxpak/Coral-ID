from django.urls import path, include
from rest_framework.routers import DefaultRouter

from core import views

router = DefaultRouter()
router.register('species', views.SpeciesViewSet)
router.register('corals', views.CoralViewSet)

urlpatterns = [
    path('predict/', views.CallModel.as_view())
]

urlpatterns += router.urls
