package edu.uniandes.moni.model

data class UserModel(
    val name: String,
    val email: String,
    val interest1: String,
    val interest2: String
) {
    constructor() : this("", "", "", "")
}


