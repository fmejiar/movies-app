package com.fmejiar.moviesapp.domain.usecase

import com.fmejiar.moviesapp.data.model.Movie
import com.fmejiar.moviesapp.data.model.MovieList
import com.fmejiar.moviesapp.domain.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUpcomingMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var moviesRepository: MoviesRepository

    lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(moviesRepository)
    }

    @Test
    fun `when the Api Upcoming Movies return anything then show the empty list`() = runBlocking {
        // Given
        coEvery { moviesRepository.getUpcomingMovies() } returns MovieList(results = emptyList())
        // When
        getUpcomingMoviesUseCase()
        // Then
        coVerify(exactly = 1) { moviesRepository.getUpcomingMovies() }
    }

    @Test
    fun `when the Api Upcoming Movies return something then get values from api`() = runBlocking {
        // Given
        val upcomingMoviesList = listOf(
            Movie(
                1,
                false,
                "/tj4lzGgHrfjnjVqAKkLIjFqPSyO.jpg",
                "Morbius",
                "en",
                "Dangerously ill with a rare blood disorder, and determined to save others suffering his same fate, Dr. Michael Morbius attempts a desperate gamble. What at first appears to be a radical success soon reveals itself to be a remedy potentially worse than the disease.",
                2060.948,
                "/6nhwr1LCozBiIN47b8oBEomOADm.jpg",
                "2022-03-30",
                "Morbius",
                false,
                5.7,
                159
            )
        )
        // Given
        coEvery { moviesRepository.getUpcomingMovies() } returns MovieList(upcomingMoviesList)
        // When
        val response = getUpcomingMoviesUseCase().results
        // Then
        coVerify(exactly = 1) { moviesRepository.getUpcomingMovies() }
        assert(upcomingMoviesList == response)

    }
}


