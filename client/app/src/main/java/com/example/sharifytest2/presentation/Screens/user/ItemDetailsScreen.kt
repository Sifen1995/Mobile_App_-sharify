package com.example.sharifytest2.presentation.Screens.user

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sharifytest2.R
import com.example.sharifytest2.presentation.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    navController: NavController,
    itemId: String,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val itemDetail by viewModel.itemDetail.collectAsState()

    LaunchedEffect(itemId) {
        Log.d("ItemDetailScreen", "Fetching details for itemId: $itemId")
        viewModel.fetchItemDetails(itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Item Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        itemDetail?.let { item ->
            val correctedImageUrl = item.image?.let { imageUrl ->
                when {
                    imageUrl.startsWith("http") -> imageUrl.replace("localhost", "10.0.2.2")
                    imageUrl.startsWith("/") -> "http://10.0.2.2:4000$imageUrl"
                    else -> imageUrl
                }
            } ?: ""

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = correctedImageUrl,
                        placeholder = painterResource(id = R.drawable.landing),
                        error = painterResource(id = R.drawable.profile)
                    ),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                StyledSection(title = "Description", content = item.description ?: "No description available")
                StyledSection(title = "Terms and Conditions", content = item.termsAndConditions ?: "No terms provided")
                StyledSection(title = "Contact Info", content = buildString {
                    append("📞 ${item.telephon ?: "No phone available"}\n")
                    append("📍 ${item.address ?: "No address available"}")
                })

                Spacer(modifier = Modifier.height(32.dp))
            }
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Loading...")
            }
        }
    }
}

@Composable
fun StyledSection(title: String, content: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            color = Color(0xFF005D73),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}
