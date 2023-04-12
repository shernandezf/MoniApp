package edu.uniandes.moni.viewmodel

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.model.adapter.SessionAdapter
import edu.uniandes.moni.model.dao.SessionDAO
import java.util.*

class SessionViewModel {

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
}