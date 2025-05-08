package com.example.sharifytest2.presentation.Screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sharifytest2.presentation.Components.BorrowingPageComponents.BorrowedItemCard
import com.example.sharifytest2.presentation.Components.BorrowingPageComponents.BorrowingNoteDialog
import com.example.sharifytest2.presentation.viewmodel.ItemViewModel




@Composable
fun BorrowingPageScreen(viewModel: ItemViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchBorrowedItems()
    }

    val borrowedItems by viewModel.borrowedItems.collectAsState()

    var showNoteDialog by remember { mutableStateOf(false) }
    var selectedItemNote by remember { mutableStateOf("") }
    var selectedItemId by remember { mutableStateOf<String?>(null) }

    if (showNoteDialog) {
        BorrowingNoteDialog(
            itemId = selectedItemId ?: "",
            initialNote = selectedItemNote,
            onDismiss = { showNoteDialog = false },
            onUpdateNote = { updatedNote ->
                selectedItemNote = updatedNote
                selectedItemId?.let { id ->
                    viewModel.updateBorrowNote(id, updatedNote)
                }
                showNoteDialog = false
            }
        )
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Borrowing",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(borrowedItems) { item ->
                    BorrowedItemCard(
                        title = item.name,
                        smallDescription = item.smalldescription ?: "No description",
                        imageUrl = item.image,
                        status = "Available", // Change this dynamically if needed
                        note = item.note ?: "",
                        onAddNoteClick = {
                            selectedItemId = item.id
                            selectedItemNote = item.note ?: ""
                            showNoteDialog = true
                        },
                        onDeleteClick = {
                            viewModel.removeFromBorrowed(item.id)
                        }
                    )
                }
            }
        }
    }
}

