package com.fmejiar.moviesapp.domain.repository

import com.fmejiar.moviesapp.core.toMovieEntity
import com.fmejiar.moviesapp.data.local.LocalMoviesDataSource
import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.data.remote.RemoteMoviesDataSource

class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource
) : MoviesRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        remoteMoviesDataSource.getUpcomingMovies().results.map { movie ->
            localMoviesDataSource.saveUpcomingMovie(movie.toMovieEntity())
        }
        return localMoviesDataSource.getLocalUpcomingMovies()
    }

}