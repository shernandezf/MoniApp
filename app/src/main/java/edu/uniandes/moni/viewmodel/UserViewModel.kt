package edu.uniandes.moni.viewmodel

import androidx.navigation.NavController
import edu.uniandes.moni.model.UserModel
import edu.uniandes.moni.model.provider.UserAdapter
import edu.uniandes.moni.navigation.AppScreens

class UserViewModel {

    private val userAdapter: UserAdapter = UserAdapter()

    companion object {
        private lateinit var userModel: UserModel

        @JvmStatic
        fun setUser(user: UserModel) {
            userModel = user
        }

        @JvmStatic
        fun getUser(): UserModel {
            return userModel
        }

    }

    fun registerUser(
        name: String,
        email: String,
        password: String,
        interest1: String,
        interest2: String
    ) {
        userAdapter.registerUser(name, email, password, interest1, interest2) { response ->
            println(response.toString())
            setUser(response)
        }
    }

    fun loginUser(email: String, password: String, navController: NavController) {
        userAdapter.loginUser(email, password) { response ->
            println(response.toString())
            setUser(response)
        }
        navController.navigate(route = AppScreens.SearchScreen.route)
    }

}