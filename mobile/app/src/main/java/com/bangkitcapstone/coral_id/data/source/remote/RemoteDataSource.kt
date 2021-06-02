package com.bangkitcapstone.coral_id.data.source.remote

import android.util.Log
import com.bangkitcapstone.coral_id.data.source.remote.network.ApiConfig
import okhttp3.MultipartBody
import retrofit2.await

class RemoteDataSource {

    suspend fun getAllCorals(callback: RemoteCallback.LoadAllCoralsCallback) {
        ApiConfig.provideApiService().getAllCorals().await().toList().let {
            callback.onAllCoralsReceived(it)
        }
    }

    suspend fun getCoralsById(coralId: Int, callback: RemoteCallback.LoadCoralByIdCallback) {
        ApiConfig.provideApiService().getCoralById(coralId).await().let {
            callback.onCoralByIdReceived(it)
        }
    }

    suspend fun postImageCoral(image: MultipartBody.Part, callback: RemoteCallback.LoadPredictionCoral) {
        ApiConfig.provideApiService().postCoralImage(image).await().result.toList().let {
            callback.onredictionCoralReceived(it)
        }
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }
}