package edu.uniandes.moni.model


data class TutoringModel(
    val description: String,
    val inUniversity: Boolean,
    val price: String,
    val title: String,
    val topic: String,
    val tutorEmail: String?,
    val reviews: Array<String>,
    val scores: Array<Int>
)

