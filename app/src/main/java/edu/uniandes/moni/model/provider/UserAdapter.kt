package edu.uniandes.moni.model.provider

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.model.UserModel

class UserAdapter {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        interest1: String,
        interest2: String,
        callback: (user: UserModel) -> Unit
    ) {
        var userModel = UserModel()
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val authResult = task.result
                        val user = authResult.user
                        val userId = user?.uid
                        if (userId != null) {
                            userModel = UserModel(name, email, interest1, interest2)
                            db.collection("users").document(userId).set(userModel)
                        }
                        callback(userModel)
                    }
                }
        }
    }

    fun loginUser(email: String, password: String, callback: (user: UserModel) -> Unit) {
        var userModel: UserModel = UserModel()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val authResult = it.result
                        val user = authResult.user
                        val userId = user?.uid
                        if (userId != null) {
                            db.collection("users").document(userId).get()
                                .addOnSuccessListener { task ->
                                    val documentValue = task.data
                                    userModel = UserModel(
                                        documentValue?.get("name") as String,
                                        documentValue["email"] as String,
                                        documentValue["interest1"] as String,
                                        documentValue["interest2"] as String,
                                    )
                                    println("This is the user get: " + userModel.toString())
                                    callback(userModel)
                                }
                        }
                    }
                }
        }
    }
}