package edu.uniandes.moni.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.uniandes.moni.model.roomDatabase.MoniDatabaseDao
import edu.uniandes.moni.model.roomDatabase.MoniRoomDB
import javax.inject.Singleton

// es un requerimiento de que solo se puede tener una clase de estas
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideMoniDao(monidatabase:MoniRoomDB): MoniDatabaseDao=monidatabase.moniDAao()

    @Singleton
    @Provides
    fun provideAppRoomDatabase(@ApplicationContext context: Context):MoniRoomDB
    =   Room.databaseBuilder(
        context,
        MoniRoomDB::class.java,
        name = "Moni_local_db" )
        .fallbackToDestructiveMigration()
        .build()

}