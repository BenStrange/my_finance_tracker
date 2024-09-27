package com.example.myfinancetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FinanceViewModel(private val repository: FinanceRepository) : ViewModel() {

    // StateFlow to expose finance items to the UI
    private val _financeItems = MutableStateFlow<List<FinanceItemEntity>>(emptyList())
    val financeItems = _financeItems.asStateFlow()

    init {
        // Load all finance items from the repository
        viewModelScope.launch {
            repository.allFinanceItems.collect { items ->
                _financeItems.value = items
            }
        }
    }

    // Function to add a new item
    fun addFinanceItem(item: FinanceItemEntity) = viewModelScope.launch {
        repository.insert(item)
    }

    // Function to remove an item
    fun removeFinanceItem(item: FinanceItemEntity) = viewModelScope.launch {
        repository.delete(item)
    }
}
