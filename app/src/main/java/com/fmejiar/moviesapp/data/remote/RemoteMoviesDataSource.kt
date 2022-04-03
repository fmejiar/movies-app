package com.fmejiar.moviesapp.data.remote

import com.fmejiar.moviesapp.application.AppConstants.API_KEY
import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.domain.repository.WebService

class RemoteMoviesDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(API_KEY)

}