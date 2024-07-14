package com.money.soypobre.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
class BudgetEntity(
    @PrimaryKey
    val id: Long,
    val description: String,
    val price: Double,
    val type: Int
)