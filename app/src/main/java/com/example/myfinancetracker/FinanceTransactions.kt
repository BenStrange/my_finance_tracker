package com.example.myfinancetracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance_transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val amount: Long,
    val date: String,
)
