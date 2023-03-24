package edu.uniandes.moni.viewmodel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.data.User

class UserViewModel {
}

fun writeNewUser(userId: String, name: String, email: String) {
    val user = User(name, email)
    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(userId).set(
        user
    )

}

fun createUser(name: String, email: String, password: String): Boolean {

    var completed = true
    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val authResult = task.result
                    val user = authResult.user
                    val userId = user?.uid
                    if (userId != null) {
                        writeNewUser(userId, name, email)
                    }
                }
                else {
                    completed = false
                }

            }


    }

    return completed
}

fun logUser(email: String, password: String): Boolean {
    var completed = true
    if (email.isNotEmpty() && password.isNotEmpty()) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful)
                    completed = false
            }
    }

    return completed
}