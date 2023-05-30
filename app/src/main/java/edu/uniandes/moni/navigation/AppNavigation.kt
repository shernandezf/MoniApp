package edu.uniandes.moni.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.monitores.HolePage
import edu.uniandes.moni.view.*
import edu.uniandes.moni.viewmodel.SessionViewModel
import edu.uniandes.moni.viewmodel.TutoringViewModel
import edu.uniandes.moni.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val sessionViewModel = hiltViewModel<SessionViewModel>()
    val userViewModel = hiltViewModel<UserViewModel>()
    val tutoringViewModel = hiltViewModel<TutoringViewModel>()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route = AppScreens.SplashScreen.route) {
            splashScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginMaterialView(navController, userViewModel)
        }
        composable(route = AppScreens.SignUpScreen.route) {
            SignupMaterialView(navController, userViewModel)
        }
        composable(route = AppScreens.SearchScreen.route) {
            HolePage(navController, tutoringViewModel)
        }

        composable(route = AppScreens.CreateTutoryScreen.route) {
            CreateTutoringScreen(navController, tutoringViewModel)
        }

        composable(route = AppScreens.MarketScreen.route) {
            MarketScreen(navController, tutoringViewModel, sessionViewModel)
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
                    tutorEmail = it.arguments?.getString("tutorEmail"),
                    tutoringViewModel = hiltViewModel<TutoringViewModel>(),
                    sessionViewModel
                )
            }
        }
        composable(route = AppScreens.CalendarScreen.route) {
            CalendarView(navController, hiltViewModel<SessionViewModel>())
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController, userViewModel)
        }

        composable(route = AppScreens.CalendarDetail.route + "/{idSession}", arguments = listOf(
            navArgument(name = "idSession") {
                type = NavType.StringType
            }
        )) {
            it.arguments?.getString("idSession")?.let { it1 ->
                CalendarDetail(
                    navController, tutoringViewModel, sessionViewModel,
                    it.arguments?.getString("idSession")!!
                )
            }

        }
    }
}