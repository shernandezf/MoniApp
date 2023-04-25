package edu.uniandes.moni.repository

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import edu.uniandes.moni.model.adapter.UserAdapter
import edu.uniandes.moni.model.roomDatabase.*
import edu.uniandes.moni.view.ConnectivityObserver
import edu.uniandes.moni.view.MainActivity
import edu.uniandes.moni.view.NetworkConnectivityObserver
import edu.uniandes.moni.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class UserRepository @Inject constructor(private val moniDatabaseDao: MoniDatabaseDao) {
    private var connectivityObserver: ConnectivityObserver= MainActivity.connectivityObserver
    private val userAdapter: UserAdapter = UserAdapter()

   suspend fun createUser(
        name: String,
        email: String,
        password: String,
        interest1: String,
        interest2: String,
        callback: (Int) -> Unit
    ){
       Log.d("internet","aaaaaaaaaaaaaa")
        connectivityObserver.observe().collect(){
            Log.d("internet","FUNCIONAAAAAAAAA $it")
            if(it==ConnectivityObserver.Status.Available) {
                Log.d("internet", "EEEEEEEEEEEEE $it")
                userAdapter.registerUser(name, email, password, interest1, interest2) { response ->
                    //println(response.toString())
                    //setUser(response)

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
                Log.d("internet","FUNCIONS")
            }
        }
    }

}