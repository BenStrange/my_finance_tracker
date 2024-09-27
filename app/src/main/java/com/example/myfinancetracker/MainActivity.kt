package com.example.myfinancetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    private lateinit var financeViewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the repository and ViewModel
        val repository = FinanceRepository(FinanceDatabase.getDatabase(application).financeDao())
        val factory = FinanceViewModelFactory(repository)
        financeViewModel = ViewModelProvider(this, factory).get(FinanceViewModel::class.java)

        // Set up the UI
        setContent {
            MaterialTheme {
                FinanceScreen(financeViewModel)
            }
        }
    }
}
