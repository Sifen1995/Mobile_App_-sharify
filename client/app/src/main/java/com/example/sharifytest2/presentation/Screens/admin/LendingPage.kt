package com.example.sharifytest2.presentation.Screens.admin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharifytest2.R
import com.example.sharifytest2.presentation.viewmodel.AdminItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LendingScreen(
    navController: NavController,
    viewModel: AdminItemViewModel = hiltViewModel(),
    onAddItemClick: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.loadItems() }
    val items by viewModel.items.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems = items.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddItemClick,
                shape = CircleShape,
                containerColor =  Color(0xFF005D73),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add" )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp , 16.dp , 16.dp , 0.dp)) {
            val start = Offset(0f, 0f)
            val end = Offset(size.width, 2f)
            drawLine(
                color = Color(0xFFe0e0e0),
                start = start,
                end = end,
                strokeWidth = 4f
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp, top = 3.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search items...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp),
                shape = RoundedCornerShape(18.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F5F5),
                    focusedBorderColor = Color.Gray ,
                    unfocusedBorderColor = Color(0xFFFd3d3d3)
                )
            )

            // Scrollable List - Filtered Items
            LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White)) {
                items(filteredItems) { item ->
                    AdminItemCard(
                        itemId = item.id,
                        title = item.name,
                        smallDescription = item.smallDescription ?: "No description",
                        imageUrl = item.image,
                        onEditClick = { selectedItemId -> navController.navigate("edit_item/$selectedItemId") },
                        onDeleteClick = {
                            viewModel.deleteItem(item.id)
                            viewModel.loadItems()
                        },
                    )
                }
            }
        }
    }
}
