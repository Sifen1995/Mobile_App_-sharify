package com.example.sharifytest2.presentation.Screens.admin

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ShortText
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sharifytest2.presentation.viewmodel.ItemViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LendItemFormScreen(
    navController: NavController,
    viewModel: ItemViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    context: Context
) {
    var name by remember { mutableStateOf("") }
    var smalldescription by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var termsAndConditions by remember { mutableStateOf("") }
    var telephon by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        println("📷 Image selected: ${uri?.path}")
    }

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    containerColor = Color.White, // ✅ White background
                    contentColor = Color.Black // ✅ Black text color
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(top = 3.dp),
                title = { Text("Lend Item") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("List an item", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Please provide the details of your item below",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Upload image row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri == null) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = "Upload Image")
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
                ) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Upload Image")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Item Info", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ItemInputField("Name*", Icons.Default.Edit, name) { name = it }
            ItemInputField("Short Description*", Icons.AutoMirrored.Filled.ShortText, smalldescription) { smalldescription = it }
            ItemInputField("Description*", Icons.Default.Description, description) { description = it }
            ItemInputField("Terms & Conditions", Icons.Default.Info, termsAndConditions) { termsAndConditions = it }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Contact Info", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ItemInputField("Phone Number", Icons.Default.PhoneIphone, telephon) { telephon = it }
            ItemInputField("Address", Icons.Default.Place, address) { address = it }

            Spacer(modifier = Modifier.height(32.dp))

            // ✅ Button to Submit Item
            Button(
                onClick = {
                    println("🔍 Preparing to send item...")

                    imageUri?.let { uri ->
                        val imagePart = prepareImagePart(uri, context)

                        if (imagePart.body.contentLength() > 0) {
                            println("✅ Image prepared successfully: ${uri.path}")

                            viewModel.addItem(
                                image = imagePart,
                                name = name,
                                smalldescription = smalldescription,
                                description = description,
                                termsAndConditions = termsAndConditions,
                                telephon = telephon,
                                address = address,
                                onSuccess = {
                                    navController.popBackStack()

                                    // ✅ Show Snackbar on Success
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "✅ Item added successfully!",
                                            actionLabel = "OK",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            )
                        } else {
                            println(" Image processing failed!")
                        }
                    } ?: println(" No image selected")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
            ) {
                Text("Submit", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Composable
fun ItemInputField(label: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = false,
        maxLines = 4
    )
}

fun prepareImagePart(uri: Uri, context: Context): MultipartBody.Part {
    println("🔍 Processing image: ${uri.path}")

    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes()

    if (bytes == null || bytes.isEmpty()) {
        println("❌ Image read failed: Bytes are null or empty!")
        throw Exception("Image processing error: No data found")
    }

    val requestBody = bytes.toRequestBody("image/*".toMediaTypeOrNull())
    println("✅ Image converted to request body successfully")
    return MultipartBody.Part.createFormData("image", "uploaded_image.jpg", requestBody)
}
