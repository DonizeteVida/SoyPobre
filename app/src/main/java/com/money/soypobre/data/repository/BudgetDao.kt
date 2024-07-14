package com.money.soypobre.data.repository

import androidx.room.Dao
import androidx.room.Query
import com.money.soypobre.data.entity.BudgetEntity

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget")
    fun getAll(): List<BudgetEntity>
}