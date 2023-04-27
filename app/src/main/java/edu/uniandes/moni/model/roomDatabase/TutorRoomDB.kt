package edu.uniandes.moni.model.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity()
data class TutorRoomDB(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name ="nombre" )
    val nombre: String,
    @ColumnInfo(name ="email" )
    val email: String,
    @ColumnInfo(name ="rating" )
    val rating: Float,
    @ColumnInfo(name ="tutoringID" )
    val tutoringID: UUID
)
