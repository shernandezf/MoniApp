package edu.uniandes.moni.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.model.repository.SessionRepository
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class SessionViewModel @Inject constructor(private val sessionRepository: SessionRepository) :
    ViewModel() {
    fun addSession(
        clientEmail: String,
        meetingDate: Date,
        place: String,
        tutorEmail: String,
        tutoringId: String,
        callback: (Int) -> Unit
    ) {
        sessionRepository.addSession(clientEmail, meetingDate, place, tutorEmail, tutoringId) {
            callback(it)
        }
    }

    fun retriveSessionsUser() {
        sessionRepository.retriveSessionsUser()
    }

    private fun getAllSessions(callback: (listaSessiones: MutableList<SessionDTO>) -> Unit) {
        sessionRepository.getAllSessions(callback)
    }
    fun getRankTutoring(callback: (String) -> Unit) {
        getAllSessions { allSessions ->
            val sessions: MutableMap<String, Int> = mutableMapOf()
            for (session in allSessions) {
                val tutoringId = session.tutoringId
                if (!sessions.containsKey(tutoringId)) {
                    sessions[tutoringId] = 0
                }
                sessions[tutoringId] = sessions[tutoringId]!! + 1
            }
            var tutoringIdMax = ""
            var cantMax = 0
            for ((clave, valor) in sessions.entries) {
                if (valor > cantMax) {
                    cantMax = valor
                    tutoringIdMax = clave
                }
            }
            callback(tutoringIdMax)
        }
    }
}