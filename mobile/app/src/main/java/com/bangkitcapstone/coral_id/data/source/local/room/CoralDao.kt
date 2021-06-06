package com.bangkitcapstone.coral_id.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity

@Dao
interface CoralDao {
    @RawQuery(observedEntities = [CoralsEntity::class])
    fun getListCorals(query: SimpleSQLiteQuery): DataSource.Factory<Int, CoralsEntity>

    @Query("SELECT * FROM corals_entities WHERE id = :coralId")
    fun getDetailCoralById(coralId: Int): LiveData<CoralsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CoralsEntity::class)
    fun insertCorals(movies: List<CoralsEntity>)
}