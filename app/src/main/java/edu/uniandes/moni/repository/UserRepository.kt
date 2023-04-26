package edu.uniandes.moni.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.uniandes.moni.communication.EmailService
import edu.uniandes.moni.model.adapter.UserAdapter
import edu.uniandes.moni.model.roomDatabase.*
import edu.uniandes.moni.view.ConnectivityObserver
import edu.uniandes.moni.view.MainActivity
import edu.uniandes.moni.view.NetworkConnectivityObserver
import edu.uniandes.moni.viewmodel.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.mail.internet.InternetAddress


class UserRepository @Inject constructor(private val moniDatabaseDao: MoniDatabaseDao, @ApplicationContext context: Context) {
    private var connectivityObserver: ConnectivityObserver= NetworkConnectivityObserver(context)
    private val userAdapter: UserAdapter = UserAdapter()

   suspend fun createUser(
        name: String,
        email: String,
        password: String,
        interest1: String,
        interest2: String,
        callback: (Int) -> Unit
    ){
        //connectivityObserver.observe().onEmpty{emit(ConnectivityObserver.Status.Lost)}
        //    .collect(){
            if(MainActivity.internetStatus=="Available") {
                Log.d("internet", "EEEEEEEEEEEEE ")
                userAdapter.registerUser(name, email, password, interest1, interest2) { response ->
                    //println(response.toString())
                    //setUser(response)
                    val auth = EmailService.UserPassAuthenticator("moniappmoviles@gmail.com", "eolkgdhtewusqqzi")
                    val to = listOf(InternetAddress("pelucapreb@gmail.com"))
                    val from = InternetAddress("moniappmoviles@gmail.com")
                    val emailS = EmailService.Email(auth, to, from, "Test Subject", "Hello Body World")
                    val emailService = EmailService("smtp.gmail.com", 587)

                    GlobalScope.launch {
                       emailService.send(emailS)
                    }
                    if (response.name == "something wrong with server") {
                        callback(1)
                    } else if (response.name == "Fill blanks") {
                        callback(2)
                    } else {
                        UserViewModel.setUser(response)
                        callback(0)
                        val user: UserRoomDB = UserRoomDB(
                            name = name,
                            email = email,
                            password = password,
                            interest1 = interest1,
                            interest2 = interest2
                        )
                        suspend { moniDatabaseDao.insertUser(user) }

                    }
                }
            }else{
              Log.d("internet","FUNCIONS $")
            }
        //}
    }

}