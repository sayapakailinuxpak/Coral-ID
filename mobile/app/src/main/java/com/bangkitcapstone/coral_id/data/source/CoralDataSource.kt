package com.bangkitcapstone.coral_id.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.local.volocal.Resource
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import okhttp3.MultipartBody

interface CoralDataSource {

    fun getAllCorals(): LiveData<Resource<PagedList<CoralsEntity>>>

    fun getCoralsById(coralId: Int): LiveData<CoralsEntity>

    fun getPredictionCoral(image: MultipartBody.Part): LiveData<List<PredictionResponse>>
}