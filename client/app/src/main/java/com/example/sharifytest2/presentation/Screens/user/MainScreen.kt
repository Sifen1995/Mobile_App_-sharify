package com.example.sharifytest2.presentation.Screens.user



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.sharifytest2.presentation.viewmodel.AuthViewModel
import androidx.compose.material3.TopAppBar // Material 3
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharifytest2.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, content: @Composable () -> Unit) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        authViewModel.loadUserRole()
    }


    LaunchedEffect(authState.success, authState.userRole) {
        if (authState.success && !hasNavigated && !authState.userRole.isNullOrEmpty()) {
            hasNavigated = true

            when (authState.userRole) {
                "admin" -> {
                    navController.navigate(Screen.AdminHome.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
                "user" -> {
                    navController.navigate(Screen.UserHome.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
                else -> {
                    navController.navigate(Screen.UserHome.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            }

            authViewModel.resetSuccess() // ✅ Match this name with ViewModel
        }
    }

    // ✅ Don't show a separate "Loading..." screen. Just wait quietly without blocking UI.
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sharify",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "User Options",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController, authState.userRole) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, userRole: String?) { // ✅ Accept userRole

    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier.height(65.dp)
    ) {
        if (userRole == "admin") {
            NavigationBarItem(
                selected = currentRoute == Screen.AdminHome.route,
                onClick = { navController.navigate(Screen.AdminHome.route) },
                label = { Text("Admin Home", fontSize = 12.sp) },
                icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Admin Home") }
            )

            NavigationBarItem(
                selected = currentRoute == Screen.UserHome.route,
                onClick = { navController.navigate(Screen.UserHome.route) },
                label = { Text("Lend", fontSize = 12.sp) },
                icon = { Icon(imageVector = Icons.Filled.IosShare, contentDescription = "Lend") }
            )

            NavigationBarItem(
                selected = currentRoute == Screen.UserHome.route,
                onClick = { navController.navigate(Screen.UserHome.route) },
                label = { Text("Lend", fontSize = 12.sp) },
                icon = { Icon(imageVector = Icons.Filled.IosShare, contentDescription = "Lend") }
            )
        } else {
            NavigationBarItem(
                selected = currentRoute == Screen.UserHome.route,
                onClick = { navController.navigate(Screen.UserHome.route) },
                label = { Text("Home", fontSize = 12.sp) },
                icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") }
            )

            NavigationBarItem(
                selected = currentRoute == Screen.UserHome.route,
                onClick = { navController.navigate(Screen.UserHome.route) },
                label = { Text("Lend", fontSize = 12.sp) },
                icon = { Icon(imageVector = Icons.Filled.IosShare, contentDescription = "Lend") }
            )

            NavigationBarItem(
                selected = currentRoute == Screen.UserHome.route,
                onClick = { navController.navigate(Screen.UserHome.route) },
                label = { Text("Lend", fontSize = 12.sp) },
                icon = { Icon(imageVector = Icons.Filled.IosShare, contentDescription = "Lend") }
            )
        }
    }
}