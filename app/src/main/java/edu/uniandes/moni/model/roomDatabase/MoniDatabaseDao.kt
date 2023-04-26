package edu.uniandes.moni.model.roomDatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoniDatabaseDao {
    //CRUD de la base de datos local

    // Get all estas no se anotan con suspend se maneja con la estructura de datos FLOW
    @Query(value = "SELECT * from SessionRoomDB")
    fun getSessiones(): Flow<List<SessionRoomDB>>

    @Query(value = "SELECT * from TutoringRoomDB")
    fun getTutorings(): Flow<List<TutoringRoomDB>>

    @Query(value = "SELECT * from TutorRoomDB")
    fun getTutors(): Flow<List<TutorRoomDB>>

    @Query(value = "SELECT * from UserRoomDB")
    fun getUsers(): Flow<List<UserRoomDB>>

    // Get element by id

    @Query(value = "SELECT * from SessionRoomDB where id =:id")
    suspend fun getSession(id: String): SessionRoomDB

    @Query(value = "SELECT * from TutoringRoomDB where id =:id")
    suspend fun getTutoring(id: String): TutoringRoomDB

    @Query(value = "SELECT * from TutorRoomDB where id =:id")
    suspend fun getTutor(id: String): TutorRoomDB

    @Query(value = "SELECT * from UserRoomDB where id =:id")
    suspend fun getUser(id: String): UserRoomDB

    //Insert element
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session:SessionRoomDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutoring(tutoring:TutoringRoomDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutor(tutor:TutorRoomDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:UserRoomDB)

    //Delete element

    @Delete
    suspend fun deleteSession(session:SessionRoomDB)

    @Delete
    suspend fun deleteTutoring(tutoring:TutoringRoomDB)

    @Delete
    suspend fun deleteTutor(tutor:TutorRoomDB)

    @Delete
    suspend fun deleteUser(user:UserRoomDB)
}