package edu.uniandes.moni.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uniandes.moni.communication.EmailService
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.model.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import javax.mail.internet.InternetAddress


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
            if(it==0) {
                viewModelScope.launch(Dispatchers.Default) {
                    sendMail(clientEmail, meetingDate, place, tutorEmail)
                }
            }
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
    suspend fun sendMail(email: String,meetingDate: Date, place: String, tutorEmail: String,){
        val auth = EmailService.UserPassAuthenticator(
            "moniappmoviles@gmail.com",
            "eolkgdhtewusqqzi"
        )
        val to = listOf(InternetAddress(email))
        val from = InternetAddress("moniappmoviles@gmail.com")
        val emailS = EmailService.Email(
            auth,
            to,
            from,
            "A session has been succefully created",
            "you have created a session with this tutor $tutorEmail \n" +
                    "at this place: $place and time: ${meetingDate.toString()} "
        )
        val emailService = EmailService("smtp.gmail.com", 587)

        emailService.send(emailS)
    }
}