package edu.uniandes.moni.repository

import edu.uniandes.moni.model.roomDatabase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoniRepository  @Inject constructor(private val moniDatabaseDao: MoniDatabaseDao){
    // get by id
    suspend fun getSession(id: String)=moniDatabaseDao.getSession(id)
    suspend fun getUser(id: String)=moniDatabaseDao.getUser(id)
    suspend fun getTutoring(id: String)=moniDatabaseDao.getTutoring(id)
    suspend fun getTutor(id: String)=moniDatabaseDao.getTutor(id)

    // insertar
    suspend fun insertSession(sesion:SessionRoomDB)=moniDatabaseDao.insertSession(sesion)
    suspend fun insertUser(user: UserRoomDB)=moniDatabaseDao.insertUser(user)
    suspend fun insertTutoring(tutoring: TutoringRoomDB)=moniDatabaseDao.insertTutoring(tutoring)
    suspend fun insertTutor(tutor:TutorRoomDB)=moniDatabaseDao.insertTutor(tutor)

    // delete

    suspend fun deleteSession(sesion:SessionRoomDB)=moniDatabaseDao.deleteSession(sesion)
    suspend fun deleteUser(user: UserRoomDB)=moniDatabaseDao.deleteUser(user)
    suspend fun deleteTutoring(tutoring: TutoringRoomDB)=moniDatabaseDao.deleteTutoring(tutoring)
    suspend fun deleteTutor(tutor:TutorRoomDB)=moniDatabaseDao.deleteTutor(tutor)

    // get all
    fun getAllSessions():Flow<List<SessionRoomDB>> = moniDatabaseDao.getSessiones().flowOn(Dispatchers.IO).conflate()
    fun getAllUsers():Flow<List<UserRoomDB>> = moniDatabaseDao.getUsers().flowOn(Dispatchers.IO).conflate()
    fun getAllTutorings():Flow<List<TutoringRoomDB>> = moniDatabaseDao.getTutorings().flowOn(Dispatchers.IO).conflate()
    fun getAllTutors():Flow<List<TutorRoomDB>> = moniDatabaseDao.getTutors().flowOn(Dispatchers.IO).conflate()
}