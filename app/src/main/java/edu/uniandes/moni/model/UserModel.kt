package edu.uniandes.moni.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel(
    val name: String,
    val email: String,
    val interest1: String,
    val interest2: String
)


