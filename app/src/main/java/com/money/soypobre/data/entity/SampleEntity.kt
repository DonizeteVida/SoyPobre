package com.money.soypobre.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sample")
class SampleEntity(
    @PrimaryKey
    val id: Long
)