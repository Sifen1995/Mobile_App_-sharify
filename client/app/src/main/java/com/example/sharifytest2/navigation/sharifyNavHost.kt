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

import com.example.sharifytest2.presentation.Screens.admin.EditItemScreen
import com.example.sharifytest2.presentation.Screens.admin.LendItemFormScreen
import com.example.sharifytest2.presentation.Screens.admin.LendingScreen

import com.example.sharifytest2.presentation.Screens.auth.SignInScreen
//import com.example.sharifytest2.Screens.SignInScreen
import com.example.sharifytest2.presentation.Screens.auth.SignUpScreen
import com.example.sharifytest2.presentation.Screens.user.AdminDashboard
import com.example.sharifytest2.presentation.Screens.user.BorrowingPageScreen
import com.example.sharifytest2.presentation.Screens.user.ItemDetailScreen

import com.example.sharifytest2.presentation.Screens.user.MainScreen
import com.example.sharifytest2.presentation.Screens.user.ProfileScreen
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
            composable(Screen.BorrowingPage.route) {
                MainScreen(navController) { BorrowingPageScreen(viewModel = hiltViewModel()) } // ✅ Load BorrowingPage correctly
            }
            composable(Screen.ProfilePage.route) {
                MainScreen(navController) { ProfileScreen(navController) } // ✅ Load ProfileScreen inside MainScreen
            }
            composable(Screen.AdminHome.route) {
                MainScreen(navController) {
                    AdminDashboard(
                        navController = navController,
                        onReviewItemsClick = {
                            navController.navigate(Screen.LendPage.route)
                        }
                    )
                }
            }


            composable(Screen.LendPage.route) {
                MainScreen(navController) {
                    LendingScreen(
                        navController = navController,
                        onAddItemClick = {
                            navController.navigate(Screen.LendItemForm.route)
                        }
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
            composable(Screen.ItemDetailPage.route + "/{itemId}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""

                ItemDetailScreen(
                    navController = navController,
                    itemId = itemId, // ✅ Pass retrieved itemId
                    viewModel = hiltViewModel() // ✅ Inject ViewModel automatically
                )
            }

            composable(Screen.LendItemForm.route) {
                LendItemFormScreen(
                    navController = navController, // ✅ Pass navController
                    viewModel = hiltViewModel(),
                    onNavigateBack = { navController.popBackStack() },
                    context = LocalContext.current
                )
            }

            composable("edit_item/{itemId}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
                EditItemScreen(navController = navController, itemId = itemId)
            }

        }






    }
}



