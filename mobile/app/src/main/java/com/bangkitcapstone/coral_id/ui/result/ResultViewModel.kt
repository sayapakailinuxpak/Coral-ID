package com.bangkitcapstone.coral_id.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class ResultViewModel @Inject constructor(private val coralRepository: CoralRepository) : ViewModel() {
    fun postImageCoral(image: MultipartBody.Part): LiveData<List<PredictionResponse>> =
        coralRepository.getPredictionCoral(image)
}