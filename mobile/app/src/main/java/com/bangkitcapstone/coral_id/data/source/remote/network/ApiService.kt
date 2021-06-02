package com.bangkitcapstone.coral_id.data.source.remote.network

import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.ListResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("corals")
    fun getAllCorals(): Call<ArrayList<CoralsResponse>>

    @GET("corals/{coral_id}")
    fun getCoralById(
        @Path("coral_id") coralId: Int
    ): Call<CoralsResponse>

    @Multipart
    @POST("predict/")
    fun postCoralImage(
        @Part image: MultipartBody.Part
    ): Call<ListResponse<PredictionResponse>>
}