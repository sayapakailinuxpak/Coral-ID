package com.bangkitcapstone.coral_id.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse

class DetailViewModel(private val coralRepository: CoralRepository) : ViewModel() {
    fun getCoralById(coralId: Int): LiveData<CoralsResponse> =
        coralRepository.getCoralsById(coralId)
}