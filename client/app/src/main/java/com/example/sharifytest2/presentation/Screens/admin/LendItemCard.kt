package com.example.sharifytest2.presentation.Screens.admin

import android.R.attr.top
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AdminItemCard(
    itemId: String,
    title: String,
    smallDescription: String,
    imageUrl: String,
    onEditClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(2.dp, Color(0xFFdbdbdb))

    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.weight(1f).background(Color.White)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Text(smallDescription, style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier= Modifier.padding(top= 4.dp))
                Text("Status: Available", style = MaterialTheme.typography.bodySmall ,
                    fontWeight = FontWeight.Bold,
                    modifier=Modifier.padding(top= 12.dp))
                Spacer(modifier = Modifier.height(24.dp))
                Row (modifier= Modifier.background(Color.White).padding(top = 16.dp , bottom = 4.dp)){
                    Button(
                        onClick = { onEditClick(itemId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(100.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
                    ) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Button(
                        onClick = onDeleteClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(100.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFF005D73))
                    ) {
                        Text("Cancel", color = Color(0xFF005D73))
                    }
                }
            }


            Spacer(modifier = Modifier.width(16.dp))

            val correctedImageUrl = imageUrl.replace("localhost", "10.0.2.2")
            Image(
                painter = rememberAsyncImagePainter(correctedImageUrl),
                contentDescription = "Item Image",
                modifier = Modifier
                    .requiredSize(88.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )
        }
    }
}
