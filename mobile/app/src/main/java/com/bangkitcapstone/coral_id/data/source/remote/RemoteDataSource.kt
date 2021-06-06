package com.bangkitcapstone.coral_id.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkitcapstone.coral_id.data.source.remote.network.ApiService
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.voremote.VoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.await
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getAllCorals(): LiveData<VoApi<List<CoralsResponse>>> {
        val resultCorals = MutableLiveData<VoApi<List<CoralsResponse>>>()
        CoroutineScope(IO).launch {
            try {
                val response = apiService.getAllCorals().await()
                resultCorals.postValue(VoApi.success(response))
            } catch (e: IOException) {
                e.printStackTrace()
                resultCorals.postValue(
                    VoApi.error(e.message, mutableListOf())
                )
            }
        }
        return resultCorals
    }

    fun postImageCoral(image: MultipartBody.Part, callback: RemoteCallback.LoadPredictionCoral) {
        CoroutineScope(IO).launch {
            try {
                apiService.postCoralImage(image).await().result.toList().let {
                    callback.onredictionCoralReceived(it)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}