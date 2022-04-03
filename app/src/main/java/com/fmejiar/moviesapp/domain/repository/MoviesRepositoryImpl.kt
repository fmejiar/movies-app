package com.fmejiar.moviesapp.domain.repository

import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.data.remote.RemoteMoviesDataSource

class MoviesRepositoryImpl(private val remoteMoviesDataSource: RemoteMoviesDataSource): MoviesRepository {

    override suspend fun getUpcomingMovies(): MovieList = remoteMoviesDataSource.getUpcomingMovies()

}