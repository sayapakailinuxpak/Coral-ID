package com.bangkitcapstone.coral_id.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bangkitcapstone.coral_id.data.source.local.LocalDataSource
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.local.volocal.Resource
import com.bangkitcapstone.coral_id.data.source.remote.RemoteCallback
import com.bangkitcapstone.coral_id.data.source.remote.RemoteDataSource
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import com.bangkitcapstone.coral_id.data.source.remote.voremote.VoApi
import okhttp3.MultipartBody
import javax.inject.Inject

class CoralRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CoralDataSource {
    override fun getAllCorals(): LiveData<Resource<PagedList<CoralsEntity>>> {
        return object : NetworkBoundResource<PagedList<CoralsEntity>, List<CoralsResponse>>() {
            public override fun loadFromDB(): LiveData<PagedList<CoralsEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localDataSource.getListCorals(), config).build()
            }

            override fun shouldFetch(data: PagedList<CoralsEntity>?): Boolean =
                data == null || data.isEmpty()


            public override fun createCall(): LiveData<VoApi<List<CoralsResponse>>> =
                remoteDataSource.getAllCorals()

            public override fun saveCallResult(data: List<CoralsResponse>) {
                val coralList = ArrayList<CoralsEntity>()
                for (response in data) {
                    val coral = CoralsEntity(
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
                    coralList.add(coral)
                }

                localDataSource.insertCorals(coralList)
            }

        }.asLiveData()
    }

    override fun getCoralsById(coralId: Int): LiveData<CoralsEntity> =
        localDataSource.getDetailCoral(coralId)

    override fun getPredictionCoral(image: MultipartBody.Part): LiveData<List<PredictionResponse>> {
        val getPrediction = MutableLiveData<List<PredictionResponse>>()
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
        return getPrediction
    }
}
