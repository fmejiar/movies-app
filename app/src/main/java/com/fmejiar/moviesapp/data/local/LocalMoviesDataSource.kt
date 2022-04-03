package com.fmejiar.moviesapp.data.local

import com.fmejiar.moviesapp.core.toMovieList
import com.fmejiar.moviesapp.data.model.MovieEntity
import com.fmejiar.moviesapp.data.model.MovieList

class LocalMoviesDataSource(private val moviesDao: MoviesDao) {

    suspend fun getLocalUpcomingMovies(): MovieList {
        return moviesDao.getRoomUpcomingMovies().toMovieList()
    }

    suspend fun saveUpcomingMovie(movie: MovieEntity) = moviesDao.saveUpcomingMovie(movie)
}