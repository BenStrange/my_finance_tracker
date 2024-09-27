package com.example.myfinancetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FinanceViewModelFactory(private val repository: FinanceRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
            return FinanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
