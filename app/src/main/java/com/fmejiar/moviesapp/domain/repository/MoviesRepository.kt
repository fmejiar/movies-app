package com.fmejiar.moviesapp.domain.repository

import com.fmejiar.moviesapp.data.model.MovieList

interface MoviesRepository {

    suspend fun getUpcomingMovies(): MovieList

}