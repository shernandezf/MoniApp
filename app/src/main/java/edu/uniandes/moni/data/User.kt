package edu.uniandes.moni.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@IgnoreExtraProperties
data class User(val name: String, val email: String, val interest1: String, val interest2: String) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.


}


