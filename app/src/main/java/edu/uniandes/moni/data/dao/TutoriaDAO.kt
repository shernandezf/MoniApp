package edu.uniandes.moni.data.dao

data class TutoriaDAO(
    val description:String,
    val inUniversity:Boolean,
    val price:String,
    val title: String,
    val topic:String,
    val tutorEmail: String?,
    val id: String
){
    constructor(): this("", false, "", "", "", "", "")
}
