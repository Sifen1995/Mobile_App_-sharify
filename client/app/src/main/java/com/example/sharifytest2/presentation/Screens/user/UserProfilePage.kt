
package com.example.sharifytest2.presentation.Screens.user

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sharifytest2.presentation.viewmodel.AuthViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val user by viewModel.userState.collectAsState()
    val message by viewModel.message.collectAsState()
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var name by remember { mutableStateOf(user?.name.orEmpty()) }
    var email by remember { mutableStateOf(user?.email.orEmpty()) }

    // Show toast for messages
    LaunchedEffect(message) {
        message?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Add scroll for smaller screens
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF005D73),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Image - Smaller rectangle
            Box(
                modifier = Modifier
                    .size(150.dp) // Smaller fixed size
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            ) {
                val imagePainter = rememberAsyncImagePainter(selectedImageUri ?: user?.profilePicture.orEmpty())
                Image(
                    painter = imagePainter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Change Picture Button
            Button(
                onClick = { launcher.launch("image/*") },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
            ) {
                Text("Change Picture")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Editable Profile Input Fields
            ProfileInputField(
                label = "Name",
                value = name,
                onValueChange = { newName -> name = newName }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileInputField(
                label = "Email",
                value = email,
                onValueChange = { newEmail -> email = newEmail }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Save Changes Button (now visible at the bottom)
            Button(
                onClick = {
                    val imagePart = selectedImageUri?.let { prepareImagePart(it, context) }
                    viewModel.updateProfile(user?.id.orEmpty(), name, imagePart)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Ensure it's not cut off
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
            ) {
                Text("Save Changes", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ProfileInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFF005D73),
            focusedBorderColor = Color(0xFF005D73)
        )
    )
}

private fun prepareImagePart(uri: Uri, context: Context): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "profile_image.jpg")
    file.outputStream().use { output -> inputStream.copyTo(output) }
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}