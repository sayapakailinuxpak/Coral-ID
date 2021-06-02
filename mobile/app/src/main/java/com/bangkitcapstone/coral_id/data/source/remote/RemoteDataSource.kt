package com.bangkitcapstone.coral_id.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkitcapstone.coral_id.data.source.remote.network.ApiConfig
import com.bangkitcapstone.coral_id.data.source.remote.network.ApiService
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
import com.bangkitcapstone.coral_id.data.source.remote.voremote.VoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class RemoteDataSource {
    /*fun getAllSpecies(callback: RemoteCallback.LoadAllSpeciesCallback) {
        CoroutineScope(IO).launch {
            try {
                ApiConfig.provideApiService().getAllSpecies().await().let {
                    callback.onAllSpeciesReceived(it)
                    Log.d("Lihatt", it.toString())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }*/

    fun getAllSpecies(): LiveData<VoApi<List<SpeciesResponse>>> {
        val resultSpecies = MutableLiveData<VoApi<List<SpeciesResponse>>>()
        CoroutineScope(IO).launch {
            try {
                val response = ApiConfig.provideApiService().getAllSpecies().await()
                Log.d("lihat remote", response.toString())
                resultSpecies.postValue(VoApi.success(response))
            } catch (e: IOException) {
                e.printStackTrace()
                resultSpecies.postValue(VoApi.error(e.message, mutableListOf()))
            }
        }
        return resultSpecies
    }

    fun getAllCorals(callback: RemoteCallback.LoadAllCoralsCallback) {
        CoroutineScope(IO).launch {
            try {
                ApiConfig.provideApiService().getAllCorals().await().let {
                    callback.onAllCoralsReceived(it)
                    Log.d("Lihatt", it.toString())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
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