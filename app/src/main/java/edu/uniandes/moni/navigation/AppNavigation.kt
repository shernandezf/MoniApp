package edu.uniandes.moni.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitores.HolePage
import edu.uniandes.moni.model.UserModel
import edu.uniandes.moni.view.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var userModel: UserModel? = null
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginMaterialView(navController)
        }
        composable(route = AppScreens.SignUpScreen.route) {
            SignupMaterialView(navController)
        }
        composable(route = AppScreens.SearchScreen.route) {
            HolePage(navController)
        }

        composable(route = AppScreens.CreateTutoryScreen.route) {
            CreateTutoryScreen(navController)
        }

        composable(route = AppScreens.MarketScreen.route) {
            MarketScreen(navController)
        }

        composable(route = AppScreens.BookTutoringScreen.route + "/{id}" + "/{title}" + "/{description}" + "/{rate}" + "/{tutorEmail}",
            arguments = listOf(
                navArgument(name = "title") {
                    type = NavType.StringType
                },
                navArgument(name = "description") {
                    type = NavType.StringType
                },
                navArgument(name = "rate") {
                    type = NavType.StringType
                },
                navArgument(name = "id") {
                    type = NavType.StringType
                },
                navArgument(name = "tutorEmail") {
                    type = NavType.StringType
                }

            )) {
            it.arguments?.getString("id")?.let { it1 ->
                BookTutoringScreen(
                    navController,
                    id = it1,
                    tutoringTitle = it.arguments?.getString("title"),
                    description = it.arguments?.getString("description"),
                    rate = it.arguments?.getString("rate"),
                    tutorEmail = it.arguments?.getString("tutorEmail")
                )
            }
        }
        composable(route = AppScreens.CalendarScreen.route) {
            CalendarView(navController)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}