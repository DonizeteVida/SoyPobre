package com.money.soypobre.data.room

import androidx.room.Dao
import androidx.room.Query
import com.money.soypobre.data.entity.SampleEntity

@Dao
interface SampleDao {
    @Query("SELECT * FROM sample")
    fun getAll(): List<SampleEntity>
}