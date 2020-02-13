package com.sweetlime.moviescollector.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sweetlime.moviescollector.db.dao.MovieDao
import com.sweetlime.moviescollector.db.entity.Movie

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
public abstract class MovieRoomDB : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{

        @Volatile
        private var INSTANCE: MovieRoomDB? = null

        fun getDatabase(context: Context): MovieRoomDB {
            val tempInstance =
                INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDB::class.java,
                    "movie_database"
                    ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}