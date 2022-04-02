package com.fmejiar.moviesapp.domain.repository

import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.data.remote.MoviesDataSource

class MoviesRepositoryImpl(private val moviesDataSource: MoviesDataSource): MoviesRepository {

    override suspend fun getUpcomingMovies(): MovieList = moviesDataSource.getUpcomingMovies()

}