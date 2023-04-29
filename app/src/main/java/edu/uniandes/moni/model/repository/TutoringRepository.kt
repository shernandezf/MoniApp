package edu.uniandes.moni.model.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.uniandes.moni.model.adapter.TutoringAdapter
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.model.roomDatabase.MoniDatabaseDao
import edu.uniandes.moni.model.roomDatabase.TutoringRoomDB
import edu.uniandes.moni.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

class TutoringRepository @Inject constructor(
    private val moniDatabaseDao: MoniDatabaseDao,
    @ApplicationContext context: Context
) {
    private val tutoringAdapter: TutoringAdapter = TutoringAdapter()
    suspend fun getAllTutorings(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        if (MainActivity.internetStatus == "Available") {
            val executor = Executors.newSingleThreadExecutor()
            val runnable = Runnable {
                tutoringAdapter.getAllTutorings {
                    CoroutineScope(Dispatchers.IO).launch {
                        for (tutoring in it) {
//                            println("Tutoring title: " + tutoring.title)
                            if (moniDatabaseDao.getTutoringByFirebaseId(tutoring.id) == 0) {
                                moniDatabaseDao.insertTutoring(
                                    TutoringRoomDB(
                                        description = tutoring.description,
                                        inUniversity = tutoring.inUniversity,
                                        price = tutoring.price,
                                        title = tutoring.title,
                                        topic = tutoring.topic,
                                        tutorEmail = tutoring.tutorEmail,
                                        idFirebase = tutoring.id
                                    )
                                )
                            }
                        }
                    }
                    callback(it)
                }
            }
            executor.execute(runnable)
            getAllTutoringsLocal {
                callback(it)
            }
        } else {
            getAllTutoringsLocal {
                callback(it)
            }
        }
    }

    private suspend fun getAllTutoringsLocal(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        moniDatabaseDao.getTutorings().distinctUntilChanged().collect() {
            val tutoringList = mutableListOf<TutoringDTO>()
            for (tutoring in it) {
                tutoringList.add(
                    TutoringDTO(
                        tutoring.description,
                        tutoring.inUniversity,
                        tutoring.price,
                        tutoring.title,
                        tutoring.topic,
                        tutoring.tutorEmail,
                        tutoring.id.toString()
                    )
                )
            }
            callback(tutoringList)
        }
    }
}