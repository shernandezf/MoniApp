package edu.uniandes.moni.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uniandes.moni.communication.EmailService
import edu.uniandes.moni.model.UserModel
import edu.uniandes.moni.model.adapter.UserAdapter
import edu.uniandes.moni.model.repository.SessionRepository
import edu.uniandes.moni.model.repository.UserRepository
import edu.uniandes.moni.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.mail.internet.InternetAddress

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

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
        interest2: String,
        callback: (Int,String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO){
                  userRepository.createUser(name, email, password, interest1, interest2) { numero,mensaje->
                        callback(numero,mensaje)
                      if (numero==0){
                      viewModelScope.launch(Dispatchers.Default){
                        sendMail(email)
                      } }
                }

        }
    }

    fun loginUser(email: String, password: String, callback: (Int) -> Unit) {
        userRepository.loginUser(email, password) {
            if (it == 0) {
                sessionRepository.retriveSessionsUser()
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
        if (MainActivity.internetStatus == "Available") {
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
        else{
            callback(4)
        }
    }
 suspend fun sendMail(email: String){
     val auth = EmailService.UserPassAuthenticator(
         "moniappmoviles@gmail.com",
         "eolkgdhtewusqqzi"
     )
     val to = listOf(InternetAddress(email))
     val from = InternetAddress("moniappmoviles@gmail.com")
     val emailS = EmailService.Email(
         auth,
         to,
         from,
         "MoniApp",
         "Your account have been created successfully in MoniApp"
     )
     val emailService = EmailService("smtp.gmail.com", 587)

     emailService.send(emailS)
 }
}