package com.bangkitcapstone.coral_id.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkitcapstone.coral_id.data.source.remote.RemoteCallback
import com.bangkitcapstone.coral_id.data.source.remote.RemoteDataSource
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
import com.bangkitcapstone.coral_id.data.source.remote.voremote.VoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoralRepository(private val remoteDataSource: RemoteDataSource) : CoralDataSource {

    /*override fun getAllSpecies(): LiveData<List<SpeciesResponse>> {
        val getSpecies = MutableLiveData<List<SpeciesResponse>>()
        remoteDataSource.getAllSpecies( object : RemoteCallback.LoadAllSpeciesCallback {
            override fun onAllSpeciesReceived(speciesResponse: List<SpeciesResponse>) {
                val speciesList = ArrayList<SpeciesResponse>()
                for (response in speciesResponse) {
                    val species = SpeciesResponse(
                        response.id,
                        response.name,
                        response.corals
                    )
                    speciesList.add(species)
                }
                getSpecies.postValue(speciesList)
            }
        })
        return getSpecies
    }*/

    override fun getAllSpecies(): LiveData<List<SpeciesResponse>> {
        val getSpecies = MutableLiveData<List<SpeciesResponse>>()
        remoteDataSource.getAllSpecies().also {
            Log.d("lihat repo", it.value?.message.toString())
            val speciesList = ArrayList<SpeciesResponse>()
            val data = it.value?.data
            if (data != null) {
                for (response in data) {
                    val species = SpeciesResponse(
                        response.id,
                        response.name,
                        response.corals
                    )
                    speciesList.add(species)
                }
                getSpecies.postValue(speciesList)
            }
        }
        return getSpecies
    }

    override fun getAllCorals(): LiveData<List<CoralsResponse>> {
        val getCorals = MutableLiveData<List<CoralsResponse>>()
        remoteDataSource.getAllCorals( object : RemoteCallback.LoadAllCoralsCallback {
            override fun onAllCoralsReceived(coralsResponse: List<CoralsResponse>) {
                val coralsList = ArrayList<CoralsResponse>()
                for (response in coralsResponse) {
                    val coral = CoralsResponse(
                        response.id,
                        response.coralGenus,
                        response.coralFamily,
                        response.discoverer,
                        response.yearDiscovered,
                        response.characteristic,
                        response.kindOfLookAlike,
                        response.distribution,
                        response.coralSpecies
                    )
                    coralsList.add(coral)
                }
                getCorals.postValue(coralsList)
            }
        })
        return getCorals
    }

    companion object {
        @Volatile
        private var instance: CoralRepository? = null

        fun getInstance(remoteData: RemoteDataSource): CoralRepository =
            instance ?: synchronized(this) {
                CoralRepository(remoteData).apply { instance = this }
            }
    }


}
