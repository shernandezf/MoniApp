package edu.uniandes.moni.model.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.uniandes.moni.model.adapter.UserAdapter
import edu.uniandes.moni.model.roomDatabase.MoniDatabaseDao
import edu.uniandes.moni.model.roomDatabase.UserRoomDB
import edu.uniandes.moni.view.MainActivity
import edu.uniandes.moni.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val moniDatabaseDao: MoniDatabaseDao,
    @ApplicationContext context: Context
) {
    private val userAdapter: UserAdapter = UserAdapter()

    suspend fun createUser(
        name: String,
        email: String,
        password: String,
        interest1: String,
        interest2: String,
        callback: (Int, String) -> Unit
    ) {
        if (MainActivity.internetStatus == "Available") {
            userAdapter.registerUser(name, email, password, interest1, interest2) { response ->
                if (response.name == "something wrong with server") {
                    callback(1, response.email)
                } else if (response.name == "Fill blanks") {
                    callback(2, "")
                } else {
                    UserViewModel.setUser(response)
                    callback(0, "")
                    val user = UserRoomDB(
                        name = name,
                        email = email,
                        password = password,
                        interest1 = interest1,
                        interest2 = interest2
                    )
                    CoroutineScope(Dispatchers.IO).launch { moniDatabaseDao.insertUser(user) }


                }
            }
        } else {
            callback(3, "")
        }

    }

    fun loginUser(email: String, password: String, callback: (Int) -> Unit) {
        if (MainActivity.internetStatus == "Available") {
            userAdapter.loginUser(email, password) { response ->
                if (response.email != "nofunciono") {
                    UserViewModel.setUser(response)

                    callback(0)
                } else {

                    callback(1)
                }

            }

        } else {
            callback(3)
        }
    }

}