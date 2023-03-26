package edu.uniandes.moni.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val name: String, val email: String, val interest1: String, val interest2: String)


