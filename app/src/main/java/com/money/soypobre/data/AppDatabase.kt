package com.money.soypobre.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.money.soypobre.data.entity.BudgetEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        BudgetEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val budgetDao: BudgetDao
}