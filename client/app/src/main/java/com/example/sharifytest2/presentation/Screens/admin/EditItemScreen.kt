package com.example.sharifytest2.presentation.Screens.admin

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sharifytest2.presentation.viewmodel.AdminItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemScreen(
    navController: NavController,
    viewModel: AdminItemViewModel = hiltViewModel(),
    itemId: String
) {
    val context = LocalContext.current
    val itemDetail by viewModel.itemDetail.collectAsState()
    val updateStatus by viewModel.updateStatus.collectAsState()

    LaunchedEffect(itemId) { viewModel.fetchItemDetails(itemId) }

    if (itemDetail == null) {
        CircularProgressIndicator()
        return
    }

    // Initialize fields from itemDetail
    var name by remember { mutableStateOf(itemDetail!!.name) }
    var smallDescription by remember { mutableStateOf(itemDetail!!.smalldescription ?: "") }
    var description by remember { mutableStateOf(itemDetail!!.description ?: "") }
    var termsAndConditions by remember { mutableStateOf(itemDetail!!.termsAndConditions ?: "") }
    var telephone by remember { mutableStateOf(itemDetail!!.telephon ?: "") }
    var address by remember { mutableStateOf(itemDetail!!.address ?: "") }
    var isAvailable by remember { mutableStateOf(itemDetail!!.isAvailable) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(top = 3.dp),
                title = { Text("Edit Item") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
            Text("Edit item details", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Update the details of your item below",
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
                    Text("Update Image")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Item Info", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ItemInputField("Name*", Icons.Default.Place, name) { name = it }
            ItemInputField("Short Description*", Icons.AutoMirrored.Filled.ShortText, smallDescription) { smallDescription = it }
            ItemInputField("Description*", Icons.Default.Description, description) { description = it }
            ItemInputField("Terms & Conditions", Icons.Default.Info, termsAndConditions) { termsAndConditions = it }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Contact Info", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ItemInputField("Phone Number", Icons.Default.Phone, telephone) { telephone = it }
            ItemInputField("Address", Icons.Default.Place, address) { address = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Availability Toggle Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Available?", style = MaterialTheme.typography.bodyMedium)
                isAvailable?.let {
                    Switch(
                        checked = it,
                        onCheckedChange = { isAvailable = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF005D73),
                            checkedTrackColor = Color(0xFF005D73).copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Loading Indicator & Status Message
            when {
                updateStatus == "Updating..." -> CircularProgressIndicator()
                updateStatus != null -> Text(updateStatus!!, color = if (updateStatus!!.startsWith("Success")) Color.Green else Color.Red)
            }

            Button(
                onClick = {
                    val imagePart = imageUri?.let {
                        viewModel.prepareImagePart(it, context)
                    }
                    isAvailable?.let {
                        viewModel.updateItem(
                            itemId = itemId,
                            name = name,
                            smallDescription = smallDescription,
                            description = description,
                            termsAndConditions = termsAndConditions,
                            telephone = telephone,
                            address = address,
                            isAvailable = it,
                            imagePart = imagePart,
                            context = context
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73))
            ) {
                Text("Update Item", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

//@Composable
//fun ItemmInputField(label: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        leadingIcon = { Icon(icon, contentDescription = label) },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        shape = RoundedCornerShape(8.dp),
//        singleLine = false,
//        maxLines = 4
//    )
//}