package com.bangkitcapstone.coral_id.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity
import com.bangkitcapstone.coral_id.data.source.local.room.CoralDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val coralDao: CoralDao) {
    private val simpleQuery =
        SimpleSQLiteQuery(StringBuilder().append("SELECT * FROM corals_entities").toString())

    fun getListCorals(): DataSource.Factory<Int, CoralsEntity> =
        coralDao.getListCorals(simpleQuery)

    fun getDetailCoral(coralId: Int): LiveData<CoralsEntity> = coralDao.getDetailCoralById(coralId)

    fun insertCorals(corals: List<CoralsEntity>) = coralDao.insertCorals(corals)
}