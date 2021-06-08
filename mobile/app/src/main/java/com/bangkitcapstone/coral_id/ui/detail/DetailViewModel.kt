package com.bangkitcapstone.coral_id.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val coralRepository: CoralRepository) :
    ViewModel() {
    fun getCoralById(coralId: Int): LiveData<CoralsEntity> =
        coralRepository.getCoralsById(coralId)
}