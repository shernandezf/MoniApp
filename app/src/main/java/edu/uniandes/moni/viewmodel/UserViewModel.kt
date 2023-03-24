package edu.uniandes.moni.viewmodel

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.data.User

class UserViewModel {


    companion object {
        private var user: User = User("juan", "juanjosecordoba3@gmail.com", "i1", "i2")
        private var entry: Boolean = false
        @JvmStatic
        fun setUser1(user1: User) {
            user = user1
        }
        @JvmStatic
        fun getUser1(): User {
            return user
        }

        @JvmStatic
        fun getEntry(): Boolean {
            return entry
        }
        @JvmStatic
        fun setEntry(entrada: Boolean) {
            entry = entrada
        }

    }

}



fun writeNewUser(userId: String, name: String, email: String, interest1: String, interest2: String) {
    val user = User(name, email, interest1, interest2)
    UserViewModel.setUser1(user)
    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(userId).set(
        user
    )
}

fun createUser(name: String, email: String, password: String, interest1: String, interest2: String) {


    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val authResult = task.result
                    val user = authResult.user
                    val userId = user?.uid
                    if (userId != null) {
                        writeNewUser(userId, name, email, interest1, interest2)
                        writeNewUser(userId, name, email, interest1, interest2)
                    }
                }

            }
    }

}

fun logUser(email: String, password: String) {
    val db = FirebaseFirestore.getInstance()
    var entry = false
    if (email.isNotEmpty() && password.isNotEmpty()) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val authResult = it.result
                    val user1 = authResult.user
                    val userId = user1?.uid

                    if (userId != null) {

                        db.collection("users").document(userId).get().addOnSuccessListener {task->
                            UserViewModel.setEntry(true)
                            val user2 = task.data
                            val user = User(user2?.get("name") as String, user2?.get("email") as String, user2?.get("interest1") as String, user2?.get("interest2") as String)

                            UserViewModel.setUser1(user!!)

                        }

                    }
                }
            }
    }

}