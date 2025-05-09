package com.example.sharifytest2.presentation.Components.BorrowingPageComponents

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BorrowingNoteDialog(
    itemId: String, // ✅ Pass item ID for database update
    initialNote: String,
    onDismiss: () -> Unit,
    onUpdateNote: (String) -> Unit
) {
    var noteText by remember { mutableStateOf(TextFieldValue(initialNote)) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {},
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🗓️ My Note on this Item",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp,
                    color = Color(0xFF005D73) // ✅ Updated text color
                )
                IconButton(onClick = { onDismiss() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF005D73) // ✅ Updated icon color
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    placeholder = { Text(text = "Write your note...", color = Color.Gray) }, // ✅ Updated placeholder color
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF005D73),
                        focusedBorderColor = Color(0xFF005D73)
                    )
                )
            }
        },
        shape = RoundedCornerShape(12.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp,
        dismissButton = {
            Button(
                onClick = {
                    onUpdateNote(noteText.text) // ✅ Save to ViewModel (database)
                    noteText = TextFieldValue(noteText.text) // ✅ Keep latest note on UI
                    Toast.makeText(context, "Note Updated ✅", Toast.LENGTH_SHORT).show()
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73)) // ✅ Updated button color
            ) {
                Text(text = "Update", color = Color.White) // ✅ White button text for contrast
            }
        }
    )
}

