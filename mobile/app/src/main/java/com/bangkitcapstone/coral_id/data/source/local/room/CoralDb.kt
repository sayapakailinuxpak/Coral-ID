package com.bangkitcapstone.coral_id.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkitcapstone.coral_id.data.source.local.entity.CoralsEntity

@Database(entities = [CoralsEntity::class], version = 1, exportSchema = false)
abstract class CoralDb : RoomDatabase() {

    abstract fun dao(): CoralDao

    companion object {
        private const val DATABASE_NAME = "coral_db"

        @Volatile
        private var INSTANCE: CoralDb? = null

        fun getInstance(context: Context): CoralDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CoralDb::class.java,
                    DATABASE_NAME
                ).build()
            }
    }
}
