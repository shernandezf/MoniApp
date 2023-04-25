package edu.uniandes.moni.model.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity()
data class UserRoomDB(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name ="name" )
    val name: String,
    @ColumnInfo(name ="email" )
    val email: String,
    @ColumnInfo(name ="interest1" )
    val interest1: String,
    @ColumnInfo(name ="interest2" )
    val interest2: String
)
