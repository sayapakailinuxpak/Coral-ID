package com.bangkitcapstone.coral_id.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.ui.book.BookViewModel
import com.bangkitcapstone.coral_id.ui.detail.DetailViewModel
import com.bangkitcapstone.coral_id.ui.result.ResultViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val mCoralRepository: CoralRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(mCoralRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mCoralRepository) as T
            }
            modelClass.isAssignableFrom(BookViewModel::class.java) -> {
                BookViewModel(mCoralRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}