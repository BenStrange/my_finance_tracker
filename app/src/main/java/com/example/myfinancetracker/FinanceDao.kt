package com.example.myfinancetracker

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FinanceDao {
    @Query("SELECT * FROM finance_items")
    fun getAllItems(): Flow<List<FinanceItemEntity>>

    @Insert
    suspend fun insertItem(item: FinanceItemEntity)

    @Delete
    suspend fun deleteItem(item: FinanceItemEntity)
}


