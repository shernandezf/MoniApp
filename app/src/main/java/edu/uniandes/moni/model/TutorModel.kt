package edu.uniandes.moni.model

data class TutorModel(
    val nombre: String,
    val email: String,
    val rating: Float,
    val tutoringModel: TutoringModel
)
