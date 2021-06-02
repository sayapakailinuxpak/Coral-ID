package com.bangkitcapstone.coral_id.data.source

import androidx.lifecycle.LiveData
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import okhttp3.MultipartBody

interface CoralDataSource {

    fun getAllCorals(): LiveData<List<CoralsResponse>>

    fun getCoralsById(coralId: Int): LiveData<CoralsResponse>

    fun getPredictionCoral(image: MultipartBody.Part): LiveData<List<PredictionResponse>>
}