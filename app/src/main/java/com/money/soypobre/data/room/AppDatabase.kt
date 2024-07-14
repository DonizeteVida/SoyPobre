package com.money.soypobre.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.money.soypobre.data.entity.SampleEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        SampleEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val sampleDao: SampleDao
}