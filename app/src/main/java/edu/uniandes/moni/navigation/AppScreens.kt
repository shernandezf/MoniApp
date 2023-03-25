package edu.uniandes.moni.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen: AppScreens("log_in")
    object SignUpScreen: AppScreens("sign_up")
    object SearchScreen: AppScreens("search")
    object CreateTutoryScreen: AppScreens("create_tutory")
    object BookTutoringScreen: AppScreens("book_tutoring")
    object MarketScreen: AppScreens("market")
}
