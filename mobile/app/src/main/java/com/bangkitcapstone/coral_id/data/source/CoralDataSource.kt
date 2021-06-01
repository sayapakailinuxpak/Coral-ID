package com.bangkitcapstone.coral_id.data.source

import androidx.lifecycle.LiveData
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse

interface CoralDataSource {
    fun getAllCorals(): LiveData<List<CoralsResponse>>

    fun getAllSpecies(): LiveData<List<SpeciesResponse>>
}