package edu.uniandes.moni.model.dao

import java.util.*

data class SessionDAO(
    val clientEmail: String,
    val meetingDate: Date,
    val place: String,
    val tutorEmail: String,
    val tutoringId: String
)
