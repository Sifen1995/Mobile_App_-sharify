package com.example.sharifytest2.presentation.Screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sharifytest2.presentation.Components.authComponents.ButtonComponent
import com.example.sharifytest2.presentation.Components.authComponents.HeadingTextComponent
import com.example.sharifytest2.presentation.Components.authComponents.LoginMethod
import com.example.sharifytest2.presentation.Components.authComponents.MyTextField
import com.example.sharifytest2.presentation.Components.authComponents.NormalTextComponent
import com.example.sharifytest2.presentation.Components.authComponents.PasswordTextFieldComponent
import com.example.sharifytest2.presentation.Components.authComponents.checkboxComponent
import com.example.sharifytest2.R
import com.example.sharifytest2.navigation.Screen
import com.example.sharifytest2.presentation.viewmodel.AuthViewModel


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.authState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextComponent(value = stringResource(id = R.string.Creat))
            NormalTextComponent(value = stringResource(id = R.string.Hello))
            Spacer(modifier = Modifier.height(40.dp))

            MyTextField(
                labelValue = stringResource(R.string.name),
                painterResource = painterResource(id = R.drawable.profile),
                onTextChange = { name = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            MyTextField(
                labelValue = stringResource(R.string.Email),
                painterResource = painterResource(id = R.drawable.email),
                onTextChange = { email = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            PasswordTextFieldComponent(
                labelValue = stringResource(R.string.Password),
                painterResource = painterResource(id = R.drawable.password),
                onTextChange = { password = it }
            )

            checkboxComponent(
                value = stringResource(R.string.Terms),
                onTextSelected = {
                    navController.navigate(Screen.Terms.route)
                }
            )

            Spacer(modifier = Modifier.height(35.dp))

            ButtonComponent(value = "Sign Up") {
                Log.d("SignUpScreen", "Button clicked")
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.register(name, email, password)
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

            LoginMethod(value = "Already registered? Sign In", onTextSelected = {
                navController.navigate(Screen.SignIn.route)
            })

            // Show error or success messages
            uiState.error?.let { error ->
                LaunchedEffect(error) {
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    viewModel.clearError()
                }
            }

            if (uiState.success) {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.SignIn.route)
                    viewModel.resetSuccess()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun previewSignUpScreen() {
    SignUpScreen(navController = rememberNavController())
}