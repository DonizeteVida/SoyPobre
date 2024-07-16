package com.money.soypobre.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.money.soypobre.data.entity.BudgetEntryEntity

@Dao
interface BudgetEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<BudgetEntryEntity>)

    @Query("SELECT * FROM budget_entry")
    suspend fun getAll(): List<BudgetEntryEntity>
}