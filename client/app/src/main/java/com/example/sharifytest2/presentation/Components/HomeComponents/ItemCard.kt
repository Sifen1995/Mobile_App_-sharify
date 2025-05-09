package com.example.sharifytest2.presentation.Components.HomeComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sharifytest2.R
import com.example.sharifytest2.domain.models.userItemModel.Item
import com.example.sharifytest2.navigation.Screen
import androidx.compose.ui.layout.ContentScale

@Composable
fun ItemCard(item: Item, navController: NavController, onBorrowClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val correctedImageUrl = item.image.replace("localhost", "10.0.2.2")
            Image(
                painter = rememberAsyncImagePainter(
                    model = correctedImageUrl,
                    placeholder = painterResource(id = R.drawable.landing),
                    error = painterResource(id = R.drawable.landing)
                ),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        navController.navigate(Screen.ItemDetailPage.route + "/${item.id}")
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.name, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.smallDescription ?: "No description",
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onBorrowClick(item.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF005D73)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Borrow", color = Color.White)
            }
        }
    }
}
