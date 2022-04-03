package com.fmejiar.moviesapp.domain.usecase

import com.fmejiar.moviesapp.domain.repository.MoviesRepository

class DoLogInUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(user: String, password: String): Boolean =
        moviesRepository.doLogIn(user, password)
}