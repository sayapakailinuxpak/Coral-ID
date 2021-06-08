package com.bangkitcapstone.coral_id.di

import com.bangkitcapstone.coral_id.ui.book.BookFragment
import com.bangkitcapstone.coral_id.ui.detail.DetailFragment
import com.bangkitcapstone.coral_id.ui.home.HomeFragment
import com.bangkitcapstone.coral_id.ui.result.ResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeBookFragment(): BookFragment

    @ContributesAndroidInjector
    abstract fun contributeResultFragment(): ResultFragment
}