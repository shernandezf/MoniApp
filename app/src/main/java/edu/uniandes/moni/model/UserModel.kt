package edu.uniandes.moni.model

data class UserModel(
    val name: String,
    var email: String,
    val interest1: String,
    val interest2: String
) {
    constructor() : this("", "", "", "")
}


