package edu.uniandes.moni.viewmodel

import androidx.navigation.NavController
import com.google.firebase.firestore.auth.User
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
        println(email + "-" + password)
        userAdapter.loginUser(email, password) { response ->
            println(response.toString())
            setUser(response)
            if (response != null) {
                navController.navigate(route = AppScreens.MarketScreen.route)
            }
        }
    }

    fun changePassword(currentPassword: String, newPassword: String, confirmPassword: String, callback: (confirmation: Int) -> Unit) {

        if(currentPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if(newPassword == confirmPassword) {

                userAdapter.changePassword(currentPassword, newPassword) {
                    if(it) {
                        // Change password have been successfully - 0
                        callback(0)
                    }

                    else {
                        // Change password have not been successfully - 1
                        callback(1)
                    }
                }
            }
            else {
                // password missmatching - 2
                callback(2)
            }
        }
        else {
            //fill all the fields - 3
            callback(3)
        }

    }

}