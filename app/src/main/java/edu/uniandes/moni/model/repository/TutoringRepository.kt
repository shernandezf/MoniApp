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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class TutoringRepository @Inject constructor(
    private val moniDatabaseDao: MoniDatabaseDao,
    @ApplicationContext context: Context,
    private val cacheManager: CacheManager
) {
    private val tutoringAdapter: TutoringAdapter = TutoringAdapter()

    suspend fun getAllTutorings(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        if (MainActivity.internetStatus == "Available") {
            tutoringAdapter.getAllTutorings {
                CoroutineScope(Dispatchers.IO).launch {
                    for (tutoring in it) {
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
                        }
                    }
                }
                callback(it)
            }
        }
        getAllTutoringsLocal {
            callback(it)
        }
    }
    suspend fun getAllTutoringsTopic(topic:String,callback: (response: MutableList<TutoringDTO>,funcion:Int) -> Unit) {
        if (MainActivity.internetStatus == "Available") {
            cacheManager.putTutoringTopic(topic)
            tutoringAdapter.getAllTutoringsTopic(topic) {
                CoroutineScope(Dispatchers.IO).launch {
                    for (tutoring in it) {
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
                        }
                    }
                }
                callback(it,0)
            }
        }else{
            var llave = 0
            if (topic == "physics") {
                llave = 1

            } else if (topic == "calculus") {
                llave = 2
            } else if (topic == "dancing") {

                llave = 3
            } else if (topic == "fitness") {
                llave = 4
            }
            if (cacheManager.isEmptyTopic() == true || cacheManager?.getTutoringTopicById(llave) == null){

                val tutoringList = mutableListOf<TutoringDTO>()
                callback(tutoringList,3)
            }else {
                val tutoringList = mutableListOf<TutoringDTO>()

                moniDatabaseDao.getTutoringTopic(topic.capitalize()).collect(){
                    it.forEach { tutoringDB->
                        var tutoringdto = TutoringDTO(
                            tutoringDB.description,
                            tutoringDB.inUniversity,
                            tutoringDB.price,
                            tutoringDB.title,
                            tutoringDB.topic,
                            tutoringDB.tutorEmail,
                            tutoringDB.id.toString()
                        )
                        tutoringList.add(tutoringdto)
                    }

                    callback(tutoringList,2)
                }
            }
        }
    }

    suspend fun getTutoringById(id: String, callback: (TutoringDTO) -> Unit) {

        var tutoring = cacheManager.getTutoringById(id)
        if (tutoring != null) {
            callback(tutoring)
        } else {
            val tutoringDB = moniDatabaseDao.getTutoring(id)
            if (tutoringDB != null) {
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
            } else {
                tutoringAdapter.getTutoringById(id) {
                    tutoring = it
                    tutoring?.let { callback(it) }
                }

            }
            tutoring?.let { cacheManager.putTutoring(it) }
        }


    }

    private suspend fun getAllTutoringsLocal(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        val count = moniDatabaseDao.getCount()
        if (count > 0) {
            val tutoringList = mutableListOf<TutoringDTO>()
            moniDatabaseDao.getTutorings()
                .map { tutorings ->
                    tutorings.map { tutoring ->
                        TutoringDTO(
                            tutoring.description,
                            tutoring.inUniversity,
                            tutoring.price,
                            tutoring.title,
                            tutoring.topic,
                            tutoring.tutorEmail,
                            tutoring.id.toString()
                        )
                    }
                }
                .collect { tutoringList.addAll(it) }
            callback(tutoringList)
        }
        else {
            callback(mutableListOf())
        }
    }
}