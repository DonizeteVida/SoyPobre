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
        "LEFT JOIN budget_entry ON budget.id = budget_entry.budgetId " +
        "WHERE budget.type = :type "
    )
    fun getAll(type: Int): Flow<Map<BudgetEntity, List<BudgetEntryEntity>>>
}