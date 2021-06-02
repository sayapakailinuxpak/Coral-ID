package com.bangkitcapstone.coral_id.data.source.remote.network

import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.ListResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("corals")
    fun getAllCorals(): Call<ArrayList<CoralsResponse>>

    @GET("species")
    fun getAllSpecies(): Call<ArrayList<SpeciesResponse>>

    @GET("corals/{coral_id}")
    fun getCoralById(
        @Path("coral_id") coralId: Int
    ) : Call<CoralsResponse>

    @GET("species/{species_id}")
    fun getSpeciesById(
        @Path("species_id") speciesId: Int
    ) : Call<SpeciesResponse>
}