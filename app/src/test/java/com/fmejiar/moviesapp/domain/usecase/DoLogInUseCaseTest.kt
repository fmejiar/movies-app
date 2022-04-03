package com.fmejiar.moviesapp.domain.usecase

import com.fmejiar.moviesapp.domain.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DoLogInUseCaseTest {

    @RelaxedMockK
    private lateinit var moviesRepository: MoviesRepository

    lateinit var doLogInUseCase: DoLogInUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        doLogInUseCase = DoLogInUseCase(moviesRepository)
    }

    @Test
    fun `when login credentials are incorrect then return false`() = runBlocking {
        //Given
        coEvery { moviesRepository.doLogIn("frank", "123") } returns false
        // When
        val response = doLogInUseCase("frank", "123")
        // Then
        assert(!response)
    }

    @Test
    fun `when login credentials are correct then return true`() = runBlocking {
        //Given
        coEvery { moviesRepository.doLogIn("Admin", "Password*123") } returns true
        // When
        val response = doLogInUseCase("Admin", "Password*123")
        // Then
        assert(response)
    }

}