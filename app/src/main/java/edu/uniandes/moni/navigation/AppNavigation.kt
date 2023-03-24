package edu.uniandes.moni.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitores.HolePage
import edu.uniandes.moni.data.User
import edu.uniandes.moni.ui.BookTutoringScreen
import edu.uniandes.moni.ui.CreateTutoryScreen
import edu.uniandes.moni.ui.LogInScreen
import edu.uniandes.moni.ui.SignUpScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var user: User? = null
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) {
            LogInScreen(navController)
        }
        composable(route = AppScreens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(route = AppScreens.SearchScreen.route) {
            HolePage(navController)
        }

        composable(route = AppScreens.CreateTutoryScreen.route) {
            CreateTutoryScreen(navController)
        }

        composable(route = AppScreens.BookTutoringScreen.route + "{idTutoring}" , arguments = listOf(
            navArgument(name = "idTutoring"){
                type = NavType.StringType
            }
        )) {
            BookTutoringScreen(navController, it.arguments?.getString("idTutoring"))
        }

    }
}