package edu.uniandes.moni.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uniandes.moni.model.UserModel
import edu.uniandes.moni.model.adapter.UserAdapter
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel(){

    private val userAdapter: UserAdapter = UserAdapter()
    private val sessionVM:SessionViewModel=SessionViewModel()
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

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        interest1: String,
        interest2: String,
        callback: (Int) -> Unit
    ) {
        userRepository.createUser(name, email, password, interest1, interest2){
            callback(it)
        }
    }

    fun loginUser(email: String, password: String,callback: (Int) -> Unit) {
        userRepository.loginUser(email,password){
            if (it==0){
                sessionVM.retriveSessionsUser()
            }
            callback(it)
        }
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
        callback: (confirmation: Int) -> Unit
    ) {

        if (currentPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (newPassword == confirmPassword) {

                userAdapter.changePassword(currentPassword, newPassword) {
                    if (it) {
                        // Change password have been successfully - 0
                        callback(0)
                    } else {
                        // Change password have not been successfully - 1
                        callback(1)
                    }
                }
            } else {
                // password missmatching - 2
                callback(2)
            }
        } else {
            //fill all the fields - 3
            callback(3)
        }

    }

}