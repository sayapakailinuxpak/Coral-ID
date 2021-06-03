package com.bangkitcapstone.coral_id.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.remote.response.CoralsResponse

class BookViewModel(private val coralRepository: CoralRepository): ViewModel() {
    fun getAllCorals(): LiveData<List<CoralsResponse>> = coralRepository.getAllCorals()
}