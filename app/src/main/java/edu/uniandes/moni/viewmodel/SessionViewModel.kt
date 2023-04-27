package edu.uniandes.moni.viewmodel


import androidx.appcompat.app.AppCompatActivity
import edu.uniandes.moni.communication.EmailService
import edu.uniandes.moni.model.adapter.SessionAdapter
import edu.uniandes.moni.model.dao.SessionDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.mail.internet.InternetAddress

class SessionViewModel: AppCompatActivity() {

    private val sessionAdapter: SessionAdapter = SessionAdapter()

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
    fun retriveSessionsUser(){
        sessionAdapter.retriveSessionsUser() {
            val date = Date()
            println("Fecha actuaaal $date")
            for (element in it) {
                println("JUANNMOOO $element.meetingDate")
                println("COMPARE TUU"+element.meetingDate.compareTo(date))
                if (element.meetingDate.compareTo(date) <= 1) {
                    val auth = EmailService.UserPassAuthenticator(
                        "moniappmoviles@gmail.com",
                        "eolkgdhtewusqqzi"
                    )
                    val to = listOf(InternetAddress(element.clientEmail))
                    val from = InternetAddress("moniappmoviles@gmail.com")
                    val emailS = EmailService.Email(
                        auth,
                        to,
                        from,
                        "MoniApp",
                        "You have a booked session in less than one day"
                    )
                    val emailService = EmailService("smtp.gmail.com", 587)
                    GlobalScope.launch {
                        emailService.send(emailS)

                    }
                    break
                }
            }
        }
    }


}