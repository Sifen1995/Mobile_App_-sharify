package com.example.sharifytest2.Screens.auth

import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sharifytest2.Components.authComponents.ButtonComponent
import com.example.sharifytest2.Components.authComponents.HeadingTextComponent
import com.example.sharifytest2.Components.authComponents.MyTextField
import com.example.sharifytest2.Components.authComponents.PasswordTextFieldComponent
import com.example.sharifytest2.R
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sharifytest2.navigation.Screen
import com.example.sharifytest2.viewmodel.AuthViewModel


@Composable

fun SignInScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isAdminLogin by remember { mutableStateOf(false) }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = {
                    navController.navigate(Screen.SignUp.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Signup"
                )
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)) {

                HeadingTextComponent("Login")

                Spacer(modifier = Modifier.height(60.dp))

                MyTextField(
                    labelValue = stringResource(R.string.Email),
                    painterResource = painterResource(id = R.drawable.email),
                    onTextChange = { email = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                PasswordTextFieldComponent(
                    labelValue = stringResource(R.string.Login_password),
                    painterResource = painterResource(id = R.drawable.password),
                    onTextChange = { password = it }
                )
                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(value = "Sign In") {
                    Log.d("SignUpScreen", "Button clicked")
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.loginUser(email, password)
                    }
                }
            }
        }
    }


                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center // 👈 Centers the loading spinner
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding( top = 10.dp) // Adjust padding if needed
                                .width(50.dp) // 👈 Adjust width
                                .height(50.dp), // 👈 Adjust height
                            color = Color(0xFF005D73) // Custom teal color// 👈 Change color to your preference
                        )
                    }
                }

                // Show error or success message
                uiState.error?.let { error ->
                    LaunchedEffect(error) {
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        viewModel.clearError()
                    }
                }

                if (uiState.success) {
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()

                        when (uiState.userRole) {
                            "admin" -> navController.navigate(Screen.AdminHome.route) // Admin Dashboard
                            "user" -> navController.navigate(Screen.Terms.route) // Regular User Screen
                            else -> navController.navigate(Screen.Terms.route) // Default fallback
                        }

                        viewModel.resetSuccess()
                    }
                }

}







