package com.example.sharifytest2.presentation.Components.BorrowingPageComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun BorrowedItemCard(
    title: String,
    smallDescription: String,
    imageUrl: String,
    status: String,
    note: String,
    onAddNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = smallDescription, style = MaterialTheme.typography.bodySmall)
                Text(text = "Status: $status", style = MaterialTheme.typography.bodySmall)

                if (note.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Note: $note", style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    OutlinedButton(
                        onClick = onAddNoteClick,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFF005D73)),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Note",
                            tint = Color(0xFF005D73),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("My Note", color = Color(0xFF005D73))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFF005D73)),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Cancel", color = Color(0xFF005D73))
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Item Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}




//package com.example.sharifytest2.presentation.Components.BorrowingPageComponents
//
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
//
//@Composable
//fun BorrowedItemCard(
//    title: String,
//    smallDescription: String,
//    imageUrl: String,
//    note: String,
//    onAddNoteClick: () -> Unit,
//    onDeleteClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
//        shape = RoundedCornerShape(12.dp) // 🔥 Rounded card
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Text(
//                        text = title,
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Black,
//                        fontSize = 18.sp,
//                        maxLines = 1
//                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text(
//                        text = smallDescription,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color.Gray,
//                        fontSize = 14.sp,
//                        maxLines = 2
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(12.dp))
//
//                Image(
//                    painter = rememberAsyncImagePainter(imageUrl),
//                    contentDescription = "Item Image",
//                    modifier = Modifier
//                        .height(80.dp)
//                        .width(80.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Status section (always showing "Status - Available" for now, you can make it dynamic later)
//            Text(
//                text = "Status - Available", // Or bind this dynamically
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//
//            // If note is added, show it
//            if (note.isNotEmpty()) {
//                Text(
//                    text = "Note: $note",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.DarkGray,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//            }
//
//            // My Note and Cancel buttons
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                horizontalArrangement = Arrangement.End
//            ) {
//                TextButton(onClick = { onAddNoteClick() }) {
//                    Text(
//                        text = "My Note",
//                        color = Color.Black,
//                        fontSize = 14.sp
//                    )
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                TextButton(onClick = { onDeleteClick() }) {
//                    Text(
//                        text = "Cancel",
//                        color = Color.Red,
//                        fontSize = 14.sp
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun BorrowedItemCardPreview_EmptyNote() {
//    MaterialTheme {
//        BorrowedItemCard(
//            title = "Power Bank",
//            smallDescription = "10000mAh portable charger",
//            imageUrl = "https://example.com/powerbank.jpg",
//            note = "",
//            onAddNoteClick = { /* Do nothing in preview */ },
//            onDeleteClick = { /* Do nothing in preview */ },
//            modifier = Modifier.padding(16.dp)
//        )
//    }
//}
//
