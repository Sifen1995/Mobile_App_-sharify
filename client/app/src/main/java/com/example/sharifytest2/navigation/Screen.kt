package com.example.sharifytest2.navigation

sealed class Screen(val route: String) {
    object Getting : Screen("start")
    object SignUp : Screen("sign_up")
    object SignIn : Screen("sign_in")
    object Terms : Screen("terms")
    object AdminHome : Screen("admin_home")
}


