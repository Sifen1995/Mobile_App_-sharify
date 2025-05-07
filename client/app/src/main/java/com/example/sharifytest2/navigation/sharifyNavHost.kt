package com.example.sharifytest2.navigation


import GettingStarted

import TermsAndConditions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharifytest2.presentation.Screens.admin.AdminDashboard


import com.example.sharifytest2.presentation.Screens.auth.SignInScreen

import com.example.sharifytest2.presentation.Screens.auth.SignUpScreen
import com.example.sharifytest2.presentation.Screens.user.MainScreen
import com.example.sharifytest2.presentation.Screens.user.UserHomePage


@Composable
fun sharifyNavHost ( ) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Getting.route){

            composable(Screen.UserHome.route) {
                MainScreen(navController) { UserHomePage(navController) } // ✅ Wrap UserHomePage inside MainScreen
            }


            composable(Screen.AdminHome.route) {
                MainScreen(navController) {
                    AdminDashboard(
                        navController = navController,

                    )
                }
            }

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






    }
}
}



