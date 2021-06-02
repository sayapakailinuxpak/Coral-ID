package com.bangkitcapstone.coral_id.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkitcapstone.coral_id.data.source.remote.RemoteCallback
import com.bangkitcapstone.coral_id.data.source.remote.RemoteDataSource
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CoralRepository(private val remoteDataSource: RemoteDataSource) : CoralDataSource {

    override fun getAllCorals(): LiveData<List<CoralsResponse>> {
        val getCorals = MutableLiveData<List<CoralsResponse>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getAllCorals(object : RemoteCallback.LoadAllCoralsCallback {
                override fun onAllCoralsReceived(coralsResponse: List<CoralsResponse>) {
                    val coralsList = ArrayList<CoralsResponse>()
                    for (response in coralsResponse) {
                        val coral = CoralsResponse(
                            response.id,
                            response.fullName,
                            response.fullNameAbbreviation,
                            response.coralGenus,
                            response.coralFamily,
                            response.discoverer,
                            response.yearDiscovered,
                            response.characteristic,
                            response.kindOfLookAlike,
                            response.distribution,
                            response.coralType,
                            response.imagePath
                        )
                        coralsList.add(coral)
                    }
                    getCorals.postValue(coralsList)
                }
            })
        }
        return getCorals
    }

    override fun getCoralsById(coralId: Int): LiveData<CoralsResponse> {
        val getCorals = MutableLiveData<CoralsResponse>()
        CoroutineScope(IO).launch {
            remoteDataSource.getCoralsById(coralId, object : RemoteCallback.LoadCoralByIdCallback {
                override fun onCoralByIdReceived(coralsResponse: CoralsResponse) {
                    val coral = CoralsResponse(
                        coralsResponse.id,
                        coralsResponse.fullName,
                        coralsResponse.fullNameAbbreviation,
                        coralsResponse.coralGenus,
                        coralsResponse.coralFamily,
                        coralsResponse.discoverer,
                        coralsResponse.yearDiscovered,
                        coralsResponse.characteristic,
                        coralsResponse.kindOfLookAlike,
                        coralsResponse.distribution,
                        coralsResponse.coralType,
                        coralsResponse.imagePath
                    )
                    getCorals.postValue(coral)
                }
            })
        }
        return getCorals
    }

    override fun getPredictionCoral(image: MultipartBody.Part): LiveData<List<PredictionResponse>> {
        val getPrediction = MutableLiveData<List<PredictionResponse>>()
        CoroutineScope(IO).launch {
            remoteDataSource.postImageCoral(image, object : RemoteCallback.LoadPredictionCoral {

                override fun onredictionCoralReceived(predictionResponse: List<PredictionResponse>) {
                    val coralsList = ArrayList<PredictionResponse>()
                    for (response in predictionResponse) {
                        val coral = PredictionResponse(
                            response.id,
                            response.fullName,
                            response.imagePath,
                            response.coralType
                            )
                        coralsList.add(coral)
                    }
                    getPrediction.postValue(coralsList)
                }
            })
        }
        return getPrediction
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
