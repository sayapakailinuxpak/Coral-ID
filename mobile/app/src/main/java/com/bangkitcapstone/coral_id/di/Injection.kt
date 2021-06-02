package com.bangkitcapstone.coral_id.di

import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.remote.RemoteDataSource

object Injection {
    fun provideCatalogRepository(): CoralRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return CoralRepository.getInstance(remoteDataSource)
    }
}