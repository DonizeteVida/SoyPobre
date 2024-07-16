package com.money.soypobre.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.money.soypobre.data.entity.BudgetEntity
import com.money.soypobre.data.entity.BudgetEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<BudgetEntryEntity>)

    @Query(
        "SELECT * FROM budget " +
        "JOIN budget_entry ON budget.id = budget_entry.id"
    )
    fun getAll(): Flow<Map<BudgetEntity, List<BudgetEntryEntity>>>
}