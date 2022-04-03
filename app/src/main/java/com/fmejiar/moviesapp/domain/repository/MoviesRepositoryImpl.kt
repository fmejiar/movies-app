package com.fmejiar.moviesapp.domain.repository

import com.fmejiar.moviesapp.core.InternetCheck
import com.fmejiar.moviesapp.core.toMovieEntity
import com.fmejiar.moviesapp.data.local.LocalMoviesDataSource
import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.data.remote.RemoteMoviesDataSource

class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource
) : MoviesRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            remoteMoviesDataSource.getUpcomingMovies().results.map { movie ->
                localMoviesDataSource.saveUpcomingMovie(movie.toMovieEntity())
            }
            localMoviesDataSource.getLocalUpcomingMovies()
        } else {
            localMoviesDataSource.getLocalUpcomingMovies()
        }

    }

    override suspend fun doLogIn(user: String, password: String): Boolean =
        localMoviesDataSource.doLogIn(user, password)

}