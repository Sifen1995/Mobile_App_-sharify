package com.example.sharifytest2.presentation.Screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataThresholding
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharifytest2.presentation.Components.BorrowingPageComponents.BorrowedItemCard
import com.example.sharifytest2.presentation.Components.BorrowingPageComponents.BorrowingNoteDialog
import com.example.sharifytest2.presentation.viewmodel.AdminItemViewModel
import com.example.sharifytest2.presentation.viewmodel.ItemViewModel



@Composable
fun AdminDashboard(
    navController: NavController,
    viewModel: AdminItemViewModel = hiltViewModel(),
    onReviewItemsClick: () -> Unit
) {
    val stats by viewModel.statistics.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center), // ✅ Centers content
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally // ✅ Centers text and button
    ) {

        when {
            stats != null -> {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.People, // ✅ Icon for total users
                            contentDescription = "Users Icon",
                            tint = Color(0xFF005D73),
                            modifier = Modifier.size(36.dp)
                        )
                        Text("Total active users", color = Color(0xFF005D73))
                        Text("${stats!!.totalUsers}", fontSize = 24.sp, color = Color.Black)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.DataThresholding, // ✅ Icon for total items
                            contentDescription = "Items Icon",
                            tint = Color(0xFF005D73),
                            modifier = Modifier.size(36.dp)
                        )
                        Text("Total items", color = Color(0xFF005D73))
                        Text("${stats!!.availableItems}", fontSize = 24.sp, color = Color.Black)
                    }
                }

                Button(
                    onClick = onReviewItemsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73)) // ✅ Updated button color
                ) {
                    Text("📋 Review Items", color = Color.White) // ✅ White button text for contrast
                }
            }

            error != null -> {
                Text("❌ Error: $error", color = Color.Red)
            }

            else -> {
                CircularProgressIndicator(color = Color(0xFF005D73)) // ✅ Updated loading indicator color
            }
        }
    }
}

