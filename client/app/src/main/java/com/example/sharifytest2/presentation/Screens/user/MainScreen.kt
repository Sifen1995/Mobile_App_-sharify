package com.example.sharifytest2.presentation.Screens.user

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharifytest2.navigation.Screen

import com.example.sharifytest2.presentation.Components.HomeComponents.OptionsDialog
import com.example.sharifytest2.presentation.viewmodel.AuthViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
@Composable
fun MainScreen(navController: NavController, content: @Composable () -> Unit) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()
    var showOptionsDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        authViewModel.loadUserRole()
    }

    val context = LocalContext.current
    val userId = authState.userId ?: ""
    val logoutState = authViewModel.logoutState.collectAsState().value
    val deleteState = authViewModel.deleteState.collectAsState().value

    // Observe logout state
    LaunchedEffect(logoutState) {
        logoutState?.let { response ->
            if (response.success) {
                navController.navigate(Screen.SignIn.route) { popUpTo(0) }
            } else {
                Toast.makeText(context, "Logout failed: ${response.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Observe delete state
    LaunchedEffect(deleteState) {
        deleteState?.let { response ->
            if (response.success) {
                navController.navigate(Screen.SignIn.route) { popUpTo(0) }
            } else {
                Toast.makeText(context, "Account deletion failed: ${response.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    if (showOptionsDialog) {
        OptionsDialog(
            onDismiss = { showOptionsDialog = false },
            onLogout = {
                showOptionsDialog = false
                showLogoutDialog = true
            },
            onDelete = {
                showOptionsDialog = false
                showDeleteConfirmDialog = true
            },
            userRole = authState.userRole
        )
    }

    // ✅ Updated Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        authViewModel.logout()
                        showLogoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73)) // ✅ Updated color
                ) {
                    Text("Logout", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showLogoutDialog = false },
                    border = BorderStroke(1.dp, Color(0xFF005D73))
                ) {
                    Text("Cancel", color = Color(0xFF005D73))
                }
            },
            title = { Text("Confirm Logout", color = Color(0xFF005D73)) }, // ✅ Updated title color
            text = { Text("Are you sure you want to log out?", color = Color.Black) } // ✅ Black text
        )
    }

    // ✅ Updated Account Deletion Confirmation Dialog
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        authViewModel.deleteAccount(userId)
                        showDeleteConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005D73)) // ✅ Updated color
                ) {
                    Text("Delete Account", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDeleteConfirmDialog = false },
                    border = BorderStroke(1.dp, Color(0xFF005D73))
                ) {
                    Text("Cancel", color = Color(0xFF005D73))
                }
            },
            title = { Text("Confirm Account Deletion", color = Color(0xFF005D73)) }, // ✅ Updated title color
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.", color = Color.Black) } // ✅ Black text
        )
    }

    Scaffold(
        topBar = {
            SharifyTopBar(
                userRole = authState.userRole,
                profileImageUrl = authState.profileImageUrl,
                navController = navController,
                onUserOptionsClick = { showOptionsDialog = true },
                onLogout = { showLogoutDialog = true }
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
fun BottomNavigationBar(navController: NavController, userRole: String?) {
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
                icon = { Icon(Icons.Filled.Home, contentDescription = "Admin Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF005D73),
                    selectedTextColor = Color(0xFF005D73),
                    indicatorColor = Color(0xFFD4EAF2) // Light shade of primary color
                )
            )
            NavigationBarItem(
                selected = currentRoute == Screen.LendPage.route,
                onClick = { navController.navigate(Screen.LendPage.route) },
                label = { Text("Lend", fontSize = 12.sp) },
                icon = { Icon(Icons.Filled.IosShare, contentDescription = "Lend") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF005D73),
                    selectedTextColor = Color(0xFF005D73),
                    indicatorColor = Color(0xFFD4EAF2)
                )
            )
            NavigationBarItem(
                selected = currentRoute == Screen.ProfilePage.route,
                onClick = { navController.navigate(Screen.ProfilePage.route) },
                label = { Text("Profile", fontSize = 12.sp) },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF005D73),
                    selectedTextColor = Color(0xFF005D73),
                    indicatorColor = Color(0xFFD4EAF2)
                )
            )
        } else {
            NavigationBarItem(
                selected = currentRoute == Screen.UserHome.route,
                onClick = { navController.navigate(Screen.UserHome.route) },
                label = { Text("Home", fontSize = 12.sp) },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF005D73),
                    selectedTextColor = Color(0xFF005D73),
                    indicatorColor = Color(0xFFD4EAF2)
                )
            )
            NavigationBarItem(
                selected = currentRoute == Screen.BorrowingPage.route,
                onClick = { navController.navigate(Screen.BorrowingPage.route) },
                label = { Text("Borrowing", fontSize = 12.sp) },
                icon = { Icon(Icons.Filled.Unarchive, contentDescription = "Borrowing") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF005D73),
                    selectedTextColor = Color(0xFF005D73),
                    indicatorColor = Color(0xFFD4EAF2)
                )
            )
            NavigationBarItem(
                selected = currentRoute == Screen.ProfilePage.route,
                onClick = { navController.navigate(Screen.ProfilePage.route) },
                label = { Text("Profile", fontSize = 12.sp) },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF005D73),
                    selectedTextColor = Color(0xFF005D73),
                    indicatorColor = Color(0xFFD4EAF2)
                )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharifyTopBar(
    userRole: String?,
    profileImageUrl: String?,
    navController: NavController,
    onUserOptionsClick: () -> Unit,
    onLogout: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val screenTitle = when (currentRoute) {
        Screen.UserHome.route -> "Home"
        Screen.AdminHome.route -> "Admin Dashboard"
        Screen.ProfilePage.route -> "Profile"
        Screen.BorrowingPage.route -> "Borrowing"
        Screen.LendPage.route -> "Lending"
        else -> "Sharify"
    }

    TopAppBar(
        title = {
            Text(
                text = screenTitle,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            when {
                userRole == "admin" -> {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color.Black
                        )
                    }
                }

                currentRoute == Screen.ProfilePage.route -> {
                    TextButton(onClick = onUserOptionsClick) {
                        Text("Account", color = Color.Black)
                    }
                }

                userRole == "person" -> {
                    profileImageUrl?.let {
                        IconButton(onClick = onUserOptionsClick) {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = "User Profile Picture",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Black, CircleShape)
                            )
                        }
                    } ?: IconButton(onClick = onUserOptionsClick) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "User Options",
                            tint = Color.Black
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        )
    )
}
