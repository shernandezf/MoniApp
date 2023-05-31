package edu.uniandes.moni.model.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.uniandes.moni.utils.Converter
import edu.uniandes.moni.utils.DateConverter
import edu.uniandes.moni.utils.UUIDConverter

@Database(
    entities = [SessionRoomDB::class, TutorRoomDB::class, TutoringRoomDB::class, UserRoomDB::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverter::class, UUIDConverter::class, Converter::class)
abstract class MoniRoomDB : RoomDatabase() {

    abstract fun moniDAO(): MoniDatabaseDao
}