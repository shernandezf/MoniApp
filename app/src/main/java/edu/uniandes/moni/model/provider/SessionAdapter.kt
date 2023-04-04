package edu.uniandes.moni.model.provider

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.model.dao.SessionDAO
import java.util.*

class SessionAdapter {

    private val db = FirebaseFirestore.getInstance()

    fun getUserSessionsOnDate(
        clientEmail: String,
        tutorEmail: String,
        date: Date,
        callback: (response: MutableList<SessionDAO>) -> Unit
    ) {
        val userSessions: MutableList<SessionDAO> = mutableListOf()
        db.collection("sessions")
            .whereEqualTo("meetingDate", Timestamp(date))
            .whereEqualTo(
                FieldPath.of("clientEmail", "tutorEmail"),
                listOf(clientEmail, tutorEmail)
            )
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val session = SessionDAO(
                        document.data["clientEmail"] as String,
                        (document.data["meetingDate"] as Timestamp).toDate(),
                        document.data["place"] as String,
                        document.data["tutorEmail"] as String,
                        document.data["tutoringId"] as String
                    )
                    userSessions.add(session)
                }
                callback(userSessions)
            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting user sessions", exception)
            }
    }
}