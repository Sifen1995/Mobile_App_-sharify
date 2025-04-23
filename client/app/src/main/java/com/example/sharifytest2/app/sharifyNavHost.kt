package com.example.sharifytest2.app

import GettingStarted
import TermsAndConditions
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharifytest2.Screens.admin.AdminDashboard
import com.example.sharifytest2.Screens.auth.SignInScreen
//import com.example.sharifytest2.Screens.SignInScreen
import com.example.sharifytest2.Screens.auth.SignUpScreen

import com.example.sharifytest2.navigation.Screen


@Composable
fun sharifyNavHost ( ) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Getting.route){
            composable(Screen.Getting.route) {
                GettingStarted(navController)
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(navController)
            }

            composable(Screen.SignIn.route) {
                SignInScreen(navController)
            }

            composable(Screen.Terms.route) {
                TermsAndConditions(
                    navController
                )

            }
            composable(Screen.AdminHome.route) {
                AdminDashboard(
                    navController
                )

            }


        }

    }
}

