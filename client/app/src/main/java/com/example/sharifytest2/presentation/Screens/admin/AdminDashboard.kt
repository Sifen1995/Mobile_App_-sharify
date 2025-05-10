package com.example.sharifytest2.presentation.Screens.user

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

    Box(
        modifier = Modifier
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val start = Offset(0f, 0f)
            val end = Offset(size.width, 0f)
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
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when {
                stats != null -> {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.People,
                                contentDescription = "Users Icon",
                                tint = Color(0xFF005D73),
                                modifier = Modifier.size(56.dp).padding(bottom = 8.dp)
                            )
                            Text(
                                "Total active users", color = Color(0xFF000000), fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 20.dp),



                                )
                            Text(
                                "${stats!!.totalUsers}",
                                fontSize = 48.sp,
                                color = Color(0xFF005D73),
                                modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 48.dp),
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(4f, 4f),
                                        blurRadius = 6f
                                    ),
                                )
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Filled.DataThresholding,
                                contentDescription = "Items Icon",
                                tint = Color(0xFF005D73),
                                modifier = Modifier.size(56.dp).padding(bottom = 8.dp)
                            )
                            Text(
                                "Total items", color = Color(0xFF000000), fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                            Text(
                                "${stats!!.availableItems}",
                                fontSize = 48.sp,
                                color = Color(0xFF005D73),
                                modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 48.dp),
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(4f, 4f),
                                        blurRadius = 8f
                                    ),
                                )
                            )
                        }
                    }

                    Button(
                        onClick = onReviewItemsClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                            .height(50.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
                    ) {
                        Text(
                            "📋 Review Items", color = Color.White,
                            style = TextStyle(
                                fontSize = 28.sp,

                                )
                        )
                    }
                }

                error != null -> {
                    Text("❌ Error: $error", color = Color.Red)
                }

                else -> {
                    CircularProgressIndicator(color = Color(0xFF005D73))
                }
            }
        }


    }
}

