package edu.uniandes.moni.model.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.uniandes.moni.model.adapter.TutoringAdapter
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.model.roomDatabase.MoniDatabaseDao
import edu.uniandes.moni.model.roomDatabase.TutoringRoomDB
import edu.uniandes.moni.utils.CacheManager
import edu.uniandes.moni.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

class TutoringRepository @Inject constructor(
    private val moniDatabaseDao: MoniDatabaseDao,
    @ApplicationContext context: Context,
    private val cacheManager: CacheManager
) {
    private val tutoringAdapter: TutoringAdapter = TutoringAdapter()

    suspend fun getAllTutorings(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        if (MainActivity.internetStatus == "Available") {
            val executor = Executors.newSingleThreadExecutor()
            val runnable = Runnable {
                tutoringAdapter.getAllTutorings {
                    CoroutineScope(Dispatchers.IO).launch {
                        for (tutoring in it) {
//
                            if (moniDatabaseDao.getTutoring(tutoring.id) == null) {
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
                                println("Tutoring id: " + tutoring.id)
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

    suspend fun getTutoringById(id: String, callback: (TutoringDTO) -> Unit) {

        var tutoring = cacheManager.getTutoringById(id)
        Log.d("Buscando en cache","1. --------")
        if (tutoring != null) {
            Log.d("Buscando en cache",tutoring.id)
        }
        if(tutoring != null) {
            callback(tutoring)
        }
        else {
            val tutoringDB = moniDatabaseDao.getTutoring(id)
            Log.d("Buscando en database","2. --------")
            if (tutoring != null) {
                Log.d("Buscando en database",tutoring.id)
            }
            if(tutoringDB != null) {
                tutoring = TutoringDTO(
                    tutoringDB.description,
                    tutoringDB.inUniversity,
                    tutoringDB.price,
                    tutoringDB.title,
                    tutoringDB.topic,
                    tutoringDB.tutorEmail,
                    tutoringDB.id.toString()
                )
                callback(tutoring)
            }
            else {
                tutoringAdapter.getTutoringById(id) {
                    tutoring = it
                    Log.d("Buscando en firebase","3. --------")
                    if (tutoring != null) {
                        Log.d("Buscando en firebase", tutoring!!.id)
                    }
                    tutoring?.let { callback(it) }
                }

            }
            tutoring?.let { cacheManager.putTutoring(it) }
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