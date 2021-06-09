package com.bangkitcapstone.coral_id.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.local.volocal.Resource
import javax.inject.Inject

class BookViewModel @Inject constructor(private val coralRepository: CoralRepository) :
    ViewModel() {
    fun getAllCorals(): LiveData<Resource<PagedList<CoralsEntity>>> = coralRepository.getAllCorals()
}