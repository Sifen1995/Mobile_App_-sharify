package com.example.sharifytest2.presentation.Screens.admin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharifytest2.presentation.viewmodel.AuthViewModel


@Composable
fun AdminDashboard(navController: NavController,
                   viewModel: AuthViewModel = viewModel()
) {
    Text(text = "Your terms and conditions content goes here.")
}

