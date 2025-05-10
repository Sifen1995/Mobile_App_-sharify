package com.example.sharifytest2.presentation.Screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharifytest2.presentation.viewmodel.ItemViewModel
import com.example.sharifytest2.presentation.Components.HomeComponents.ItemCard
import kotlinx.coroutines.launch // Needed for Snackbar actions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomePage(navController: NavController, viewModel: ItemViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val items by viewModel.items.collectAsState()
    val filteredItems = items.filter { it.name.contains(searchQuery, ignoreCase = true) }

    // ✅ Snackbar Host & Coroutine Scope
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // ✅ Wrapped UI inside Scaffold for Snackbar support
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.fillMaxWidth(0.9f), // ✅ Adjust width
                    containerColor = Color.White, // ✅ White background
                    contentColor = Color.Black // ✅ White text
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 15.dp)
        ) {
            // Your UI content

          // Styled Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }, // ✅ Updates search query dynamically
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp)),
                placeholder = { Text("Search items...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                },
                shape = RoundedCornerShape(18.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color(0xFFD3D3D3)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Load items when page loads
            LaunchedEffect(Unit) {
                viewModel.loadItems()
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredItems) { item ->
                    ItemCard(
                        item = item,
                        navController = navController,
                        onBorrowClick = {
                            viewModel.borrowItem(it)

                            // ✅ Show Snackbar when item is borrowed
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "✅ Item borrowed successfully!",
                                    actionLabel = "OK",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}