package edu.uniandes.moni.model.dto

import android.os.Build
import androidx.annotation.RequiresApi

import java.util.Date

data class SessionDTO(
    val clientEmail: String,
    val meetingDate: Date,
    val place: String,
    val tutorEmail: String,
    val tutoringId: String,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", Date(), "", "", "")
}
