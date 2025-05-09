package com.example.sharifytest2.navigation

sealed class Screen(val route: String) {
    object Getting : Screen("start")
    object SignUp : Screen("sign_up")
    object SignIn : Screen("sign_in")
    object Terms : Screen("terms")
    object AdminHome : Screen("admin_home")
    object UserHome : Screen("user_home")
    object ItemDetailPage : Screen("item_detail_page")
    object BorrowingPage : Screen("borrowing_page")
    object ProfilePage : Screen("profile_page")
    object LendPage: Screen("lend_page")
    object AdminProfile: Screen("admin_profile") // ✅ New screen for admin profile
    object LendItemForm : Screen("lend_item_form")

}


