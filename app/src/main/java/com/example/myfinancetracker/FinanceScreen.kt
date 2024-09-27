package com.example.myfinancetracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FinanceScreen(viewModel: FinanceViewModel) {
    // Observe finance items from the ViewModel
    val FinanceItems by viewModel.financeItems.collectAsState()

    // State to control the visibility of the Add Item dialog
    var showAddItemDialog by remember { mutableStateOf(false) }

    // State to track the selected item for removal
    var selectedItem by remember { mutableStateOf<FinanceItemEntity?>(null) }

    // UI layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "My Finance Tracker",
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // List of finance items
        FinanceList(
            financeItems = FinanceItems,
            selectedItem = selectedItem,
            onSelectItem = { selectedItem = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Item Button
        Button(onClick = { showAddItemDialog = true }) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Remove Item Button - only enabled if an item is selected
        Button(
            onClick = {
                selectedItem?.let {
                    viewModel.removeFinanceItem(it)
                    selectedItem = null
                }
            },
            enabled = selectedItem != null
        ) {
            Text("Remove Selected Item")
        }
    }

    // Add Item Dialog
    if (showAddItemDialog) {
        AddItemDialog(
            onDismiss = { showAddItemDialog = false },
            onConfirm = { newItem ->
                viewModel.addFinanceItem(newItem)  // Add item to the ViewModel
                showAddItemDialog = false  // Hide the dialog after adding
            }
        )
    }
}

@Composable
fun FinanceList(
    financeItems: List<FinanceItemEntity>,
    selectedItem: FinanceItemEntity?,
    onSelectItem: (FinanceItemEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(financeItems) { item ->
            Text(
                text = "${item.name} - ${item.date} - £${String.format("%.2f", item.price)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onSelectItem(item) }
                    .background(if (item == selectedItem) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onConfirm: (FinanceItemEntity) -> Unit
) {
    // States for user input
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemDate by remember { mutableStateOf(getTodayDate()) }

    // Dialog for adding a new finance item
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Add Finance Item", fontSize = 20.sp)

                // Item name input
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Item price input
                TextField(
                    value = itemPrice,
                    onValueChange = { itemPrice = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Item date input (read-only)
                TextField(
                    value = itemDate,
                    onValueChange = { itemDate = it },
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    readOnly = true
                )

                // Confirm and Cancel buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        // Validate inputs before confirming
                        if (itemName.isNotEmpty() && itemPrice.isNotEmpty()) {
                            val newItem = FinanceItemEntity(
                                name = itemName,
                                date = itemDate,
                                price = itemPrice.toDouble()
                            )
                            onConfirm(newItem)
                        }
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

fun getTodayDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
    return dateFormat.format(Date())
}
