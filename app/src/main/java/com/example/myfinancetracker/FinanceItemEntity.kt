package com.example.myfinancetracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance_items")
data class FinanceItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: String,
    val price: Double
)
