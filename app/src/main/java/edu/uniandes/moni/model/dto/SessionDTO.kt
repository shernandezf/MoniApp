package edu.uniandes.moni.model.dto

import java.util.*

data class SessionDTO(
    val clientEmail: String,
    val meetingDate: Date,
    val place: String,
    val tutorEmail: String,
    val tutoringId: String,
) {
    constructor() : this("", Date(), "", "", "")
}
