package com.example.sharifytest2.presentation.Screens.user

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

@Composable
fun UserHomePage(navController: NavController, viewModel: ItemViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val items by viewModel.items.collectAsState()


    val filteredItems = items.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
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

            )

        Spacer(modifier = Modifier.height(16.dp))

        // Trigger loading
        LaunchedEffect(Unit) {
            viewModel.loadItems()
        }

        // ✅ Updated Item Grid - Filters based on Search Query
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredItems) { item -> // ✅ Show only matching items
                ItemCard(
                    item = item,
                    navController = navController,
                    onBorrowClick = {
                        viewModel.borrowItem(it)
                    }
                )
            }
        }
    }
}
