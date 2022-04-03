package com.fmejiar.moviesapp.domain.usecase

import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.domain.repository.MoviesRepository

class GetUpcomingMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): MovieList = moviesRepository.getUpcomingMovies()

}