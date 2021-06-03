package com.bangkitcapstone.coral_id.data.source.remote

import android.util.Log
import com.bangkitcapstone.coral_id.data.source.remote.network.ApiConfig
import okhttp3.MultipartBody
import retrofit2.await
import java.io.IOException

class RemoteDataSource {

    suspend fun getAllCorals(callback: RemoteCallback.LoadAllCoralsCallback) {
        try {
            ApiConfig.provideApiService().getAllCorals().await().toList().let {
                callback.onAllCoralsReceived(it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    suspend fun getCoralsById(coralId: Int, callback: RemoteCallback.LoadCoralByIdCallback) {
        try {
            ApiConfig.provideApiService().getCoralById(coralId).await().let {
                callback.onCoralByIdReceived(it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    suspend fun postImageCoral(image: MultipartBody.Part, callback: RemoteCallback.LoadPredictionCoral) {
        try {
            ApiConfig.provideApiService().postCoralImage(image).await().result.toList().let {
                callback.onredictionCoralReceived(it)
            }
        } catch (e: IOException) {
            e.printStackTrace()
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