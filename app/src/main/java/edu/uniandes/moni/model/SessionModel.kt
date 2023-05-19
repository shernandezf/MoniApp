package edu.uniandes.moni.model

import java.util.Date

data class SessionModel(
    val clientEmail: String,
    val meetingDate: Date,
    val place: String,
    val tutorEmail: String,
    val tutoringId: String
)
