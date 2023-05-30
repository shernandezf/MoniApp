package edu.uniandes.moni.model.dto

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Date

data class SessionExtendedDTO(
    val clientEmail: String,
    val meetingDate: Date,
    val place: String,
    val tutorEmail: String,
    val tutoringId: String,
    val sessionId: String,
    var tutoringTitle: String
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", Date(), "", "", "", "", "")
}
