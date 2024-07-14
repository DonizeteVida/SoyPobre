package com.money.soypobre.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.money.soypobre.data.entity.BudgetEntity

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<BudgetEntity>)

    @Query("SELECT COUNT(*) FROM budget")
    suspend fun count(): Long

    @Query("SELECT * FROM budget")
    suspend fun getAll(): List<BudgetEntity>
}