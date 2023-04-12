package edu.uniandes.moni.viewmodel

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.model.adapter.SessionAdapter
import edu.uniandes.moni.model.dao.SessionDAO
import java.util.*

class SessionViewModel: AppCompatActivity() {

    private val sessionAdapter: SessionAdapter = SessionAdapter()

    companion object {
        private var userSessions: MutableList<SessionDAO> = mutableListOf<SessionDAO>()

        @JvmStatic
        fun initSessions() {
            userSessions = mutableListOf<SessionDAO>()
        }

        @JvmStatic
        fun addSession(sessionDAO: SessionDAO) {
            println(userSessions.size)
            userSessions.add(sessionDAO)


        }

        @JvmStatic
        fun getUserSessions(): MutableList<SessionDAO> {
            println("final size is: " + userSessions.size)
            return userSessions
        }

    }

    fun addSession2(
        clientEmail: String,
        meetingDate: Date,
        place: String,
        tutorEmail: String,
        tutoringId: String,
        callback: (Int) -> Unit
    ) {
        sessionAdapter.addSession(clientEmail, meetingDate, place, tutorEmail, tutoringId) {
            if (it == 0) {
                //Session added successfully
                callback(0)
            } else if (it == 1) {
                //There was an error
                callback(1)
            }
        }

    }

    fun retrieveUserSessions(): MutableList<SessionDAO> {
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
                        || session.tutorEmail == UserViewModel.getUser().email
                    ) {
                        addSession(session)
                    }

                }
            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting user sessions", exception)
            }
        return userSessions
    }

    fun sendemail(student: String, Tutor: String, time: java.sql.Timestamp, place:String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(Tutor))
        val emailsubject="tienes una monitoria nueva pendiente"
        intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject)
        val emailbody="Tiene una nueva monitoria con el estudiante con el email: $student\\n" +
                "la tiene en esta fecha: ${time.toString()} y en este lugar: $place\\n" +
                "No olvides asistir. Muchos Exitos"

        intent.putExtra(Intent.EXTRA_TEXT, emailbody)
        intent.type = "message/rfc822"

        startActivity(Intent.createChooser(intent, "Choose an Email client :"))

    }
}