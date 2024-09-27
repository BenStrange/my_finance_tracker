package com.example.myfinancetracker

import kotlinx.coroutines.flow.Flow

class FinanceRepository(private val financeDao: FinanceDao) {

    // Get all finance items as a Flow
    val allFinanceItems: Flow<List<FinanceItemEntity>> = financeDao.getAllItems()

    // Insert a new item
    suspend fun insert(item: FinanceItemEntity) {
        financeDao.insertItem(item)
    }

    // Delete an item
    suspend fun delete(item: FinanceItemEntity) {
        financeDao.deleteItem(item)
    }
}
