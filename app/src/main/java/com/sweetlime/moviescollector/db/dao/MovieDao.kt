package com.sweetlime.moviescollector.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sweetlime.moviescollector.db.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * from movies ORDER BY name ASC")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)
}