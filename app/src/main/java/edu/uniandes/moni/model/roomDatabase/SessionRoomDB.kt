package edu.uniandes.moni.model.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity()
data class SessionRoomDB(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "clientEmail")
    val clientEmail: String,
    @ColumnInfo(name = "meetingDate")
    val meetingDate: Date,
    @ColumnInfo(name = "place")
    val place: String,
    @ColumnInfo(name = "tutorEmail")
    val tutorEmail: String,
    @ColumnInfo(name = "tutoringId")
    val tutoringId: String
)
