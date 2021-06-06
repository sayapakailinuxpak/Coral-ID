package com.bangkitcapstone.coral_id.di

import android.app.Application
import com.bangkitcapstone.coral_id.data.source.CoralRepository
import com.bangkitcapstone.coral_id.data.source.local.LocalDataSource
import com.bangkitcapstone.coral_id.data.source.local.room.CoralDao
import com.bangkitcapstone.coral_id.data.source.local.room.CoralDb
import com.bangkitcapstone.coral_id.data.source.remote.RemoteDataSource
import com.bangkitcapstone.coral_id.data.source.remote.network.ApiService
import com.bangkitcapstone.coral_id.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    companion object {

        @Singleton
        @Provides
        fun provideCoralDatabase(application: Application): CoralDb =
            CoralDb.getInstance(application)

        @Singleton
        @Provides
        fun provideCoralDao(coralDb: CoralDb): CoralDao =
            coralDb.dao()

        @Singleton
        @Provides
        fun provideLocalDataSource(coralDao: CoralDao): LocalDataSource =
            LocalDataSource(coralDao)

        @Singleton
        @Provides
        fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
            RemoteDataSource(apiService)

        @Singleton
        @Provides
        fun provideCatalogRepository(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): CoralRepository = CoralRepository(remoteDataSource, localDataSource)

        @Singleton
        @Provides
        fun provideViewModelFactory(coralRepository: CoralRepository): ViewModelFactory =
            ViewModelFactory(coralRepository)

    }
}