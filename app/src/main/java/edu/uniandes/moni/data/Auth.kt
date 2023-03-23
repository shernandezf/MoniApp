package edu.uniandes.moni.data

import com.google.firebase.auth.FirebaseAuth

class Auth {

    fun createUser(name: String = "OK", email: String, password: String): Boolean {

        var completed = true
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful)
                        completed = false
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
}