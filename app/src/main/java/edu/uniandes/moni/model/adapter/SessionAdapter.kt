package edu.uniandes.moni.model.adapter

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.model.UserModel
import edu.uniandes.moni.model.dao.SessionDAO
import edu.uniandes.moni.viewmodel.SessionViewModel
import edu.uniandes.moni.viewmodel.UserViewModel
import java.util.*

class SessionAdapter {

    private val db = FirebaseFirestore.getInstance()
    private var userSessions: MutableList<SessionDAO> = mutableListOf<SessionDAO>()
    private fun addSession(sessionDAO: SessionDAO) {
        userSessions.add(sessionDAO)
    }
    fun getUserSessions(): MutableList<SessionDAO> {
        return userSessions
    }
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

    fun addSession(
        clientEmail: String,
        meetingDate: Date,
        place: String,
        tutorEmail: String,
        tutoringId: String,
        callback: (Int) -> Unit
    ) {
        if (clientEmail != "" && place != "" && tutorEmail != "" && tutoringId != "") {
            val session: SessionDAO =
                SessionDAO(clientEmail, meetingDate, place, tutorEmail, tutoringId)
            db.collection("sessions").document().set(session).addOnCompleteListener {
                callback(0)
            }
        } else {
            callback(1)
        }
    }

    fun retriveSessionsUser(callback: (listaSessiones:MutableList<SessionDAO>)-> Unit) {
        val firestore = Firebase.firestore
        firestore.collection("sessions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var session = SessionDAO(
                        document.data?.get("clientEmail").toString(),
                        (document.data?.get("meetingDate") as Timestamp).toDate(),
                        document.data?.get("place").toString(),
                        document.data?.get("tutorEmail").toString(),
                        document.data?.get("tutoringId").toString()
                    )

                    if (session.clientEmail == UserViewModel.getUser().email
                    ) {
                        userSessions.add(session)
                    }

                }
                callback(userSessions)
            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting user sessions", exception)
            }

    }
}