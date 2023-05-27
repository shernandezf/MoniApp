package edu.uniandes.moni.model.adapter

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.viewmodel.UserViewModel
import java.util.Date

class SessionAdapter {

    private val db = FirebaseFirestore.getInstance()
    private var userSessions: MutableList<SessionDTO> = mutableListOf<SessionDTO>()
    fun getUserSessionsOnDate(
        clientEmail: String,
        tutorEmail: String,
        date: Date,
        callback: (response: MutableList<SessionDTO>) -> Unit
    ) {
        val userSessions: MutableList<SessionDTO> = mutableListOf()
        db.collection("sessions")
            .whereEqualTo("meetingDate", Timestamp(date))
            .whereEqualTo(
                FieldPath.of("clientEmail", "tutorEmail"),
                listOf(clientEmail, tutorEmail)
            )
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val session = SessionDTO(
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

        val session: SessionDTO =
            SessionDTO(clientEmail, meetingDate, place, tutorEmail, tutoringId)
        db.collection("sessions").document().set(session).addOnCompleteListener {
            // Completed 1: significa que la sesion no se pudo guardar correctamente en firebase
            // Completed 0: significa que la sesion se guardo correctamtente en fireba
            if (it.isSuccessful)
                callback(0)
            else
                callback(1)

        }

    }

    fun getSessionById(id: String, callback: (tutoring: SessionDTO) -> Unit) {
        val tutoringRef = db.collection("sessions").document(id)
        tutoringRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val session = SessionDTO(
                    documentSnapshot.data?.get("clientEmail") as String,
                    (documentSnapshot.data!!["meetingDate"] as Timestamp).toDate(),
                    documentSnapshot.data!!["place"] as String,
                    documentSnapshot.data!!["tutorEmail"] as String,
                    documentSnapshot.data!!["tutoringId"] as String
                )
                callback(session)
            }

        }.addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting the tutoring.", exception)
        }
    }

    fun retriveSessionsUser(callback: (listaSessiones: MutableList<SessionDTO>) -> Unit) {
        val firestore = Firebase.firestore
        firestore.collection("sessions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var session = SessionDTO(
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

    fun getAllSessions(callback: (listaSessiones: MutableList<SessionDTO>) -> Unit) {
        var allSessions: MutableList<SessionDTO> = mutableListOf()
        val firestore = Firebase.firestore
        firestore.collection("sessions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var session = SessionDTO(
                        document.data?.get("clientEmail").toString(),
                        (document.data?.get("meetingDate") as Timestamp).toDate(),
                        document.data?.get("place").toString(),
                        document.data?.get("tutorEmail").toString(),
                        document.data?.get("tutoringId").toString()
                    )
                    if (!session.tutoringId.isNullOrBlank())
                        allSessions.add(session)
                }
                callback(allSessions)
            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting user sessions", exception)
            }

    }
}