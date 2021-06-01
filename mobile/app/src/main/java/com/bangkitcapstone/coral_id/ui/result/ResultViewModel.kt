package com.bangkitcapstone.coral_id.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse
import com.bangkitcapstone.coral_id.data.source.remote.response.SpeciesResponse
import com.bangkitcapstone.coral_id.utils.DataDummy

class ResultViewModel(private val coralRepository: CoralRepository): ViewModel() {
    fun getAllSpecies(): LiveData<List<SpeciesResponse>> = coralRepository.getAllSpecies()
    //fun getAllCorals(): LiveData<List<CoralsResponse>> = coralRepository.getAllCorals()
}