package edu.uniandes.moni.model.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.uniandes.moni.communication.EmailService
import edu.uniandes.moni.model.adapter.SessionAdapter
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.model.roomDatabase.MoniDatabaseDao
import edu.uniandes.moni.model.roomDatabase.SessionRoomDB
import edu.uniandes.moni.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import javax.mail.internet.InternetAddress

class SessionRepository @Inject constructor(
    private val moniDatabaseDao: MoniDatabaseDao,
    @ApplicationContext context: Context
) {

    private val sessionAdapter = SessionAdapter()

    fun addSession(
        clientEmail: String,
        meetingDate: Date,
        place: String,
        tutorEmail: String,
        tutoringId: String,
        callback: (Int) -> Unit
    ) {

        if (MainActivity.internetStatus == "Available") {
            // Completed 2: significa que no hay conexion a internet
            // Completed 1: significa que la sesion no se pudo guardar correctamente en firebase
            // Completed 0: significa que la sesion se guardo correctamtente en firebase

            sessionAdapter.addSession(clientEmail, meetingDate, place, tutorEmail, tutoringId) {
                callback(it)
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                moniDatabaseDao.insertSession(
                    SessionRoomDB(
                        clientEmail = clientEmail,
                        meetingDate = meetingDate,
                        place = place,
                        tutorEmail = tutorEmail,
                        tutoringId = tutoringId
                    )
                )

                CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        Log.d("OfflineBooking", "addSession: Validating internetStatus")
                        delay(1000)
                        if (MainActivity.internetStatus == "Available") {
                            Log.d("OfflineBooking", "addSession: Recovered internet")
                            moniDatabaseDao.getSessiones().collect() {
                                for (session in it) {
                                    Log.d(
                                        "OfflineBooking",
                                        "addSession: " + session.clientEmail
                                    )
                                    sessionAdapter.addSession(
                                        session.clientEmail,
                                        session.meetingDate,
                                        session.place,
                                        session.tutorEmail,
                                        session.tutoringId
                                    ) {
//                                        callback(it)
                                    }
                                }
                            }
                            moniDatabaseDao.deleteAllSessions()
                            Log.d("OfflineBooking", "addSession: Deleted sessions")
                            break
                        }
                    }
                }
                callback(2)
            }
        }
    }

    fun retriveSessionsUser() {
        if (MainActivity.internetStatus == "Available") {
            sessionAdapter.retriveSessionsUser() {
                val date = Date()
                for (element in it) {
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
                            "You have a booked session in less than one day \n " +
                                    "your session is in: ${element.place} \n" +
                                    "at this hour: ${element.meetingDate.hours} \n" +
                                    "this is the tutors email: ${element.tutorEmail}, contact him in case of any inconvenience"
                        )
                        val emailService = EmailService("smtp.gmail.com", 587)
                        CoroutineScope(Dispatchers.IO).launch {
                            emailService.send(emailS)

                        }
                        break
                    }
                }
            }
        }
    }

    fun getAllSessions(callback: (listaSessiones: MutableList<SessionDTO>) -> Unit) {
        sessionAdapter.getAllSessions(callback)
    }
}