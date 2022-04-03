package com.fmejiar.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fmejiar.moviesapp.data.model.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movieentity")
    suspend fun getRoomUpcomingMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUpcomingMovie(movie: MovieEntity)

}