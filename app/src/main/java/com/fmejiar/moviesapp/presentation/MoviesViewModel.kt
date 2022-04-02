package com.fmejiar.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.fmejiar.moviesapp.core.UpcomingMoviesResult
import com.fmejiar.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MoviesViewModel(private val moviesRepository: MoviesRepository): ViewModel() {

    fun fetchUpcomingMovies() = liveData(Dispatchers.IO) {
       emit(UpcomingMoviesResult.Loading())
       try {
           emit(UpcomingMoviesResult.Success(moviesRepository.getUpcomingMovies()))
       } catch (e: Exception) {
           emit(UpcomingMoviesResult.Failure(e))
       }
    }

}

class MoviesViewModelFactory(private val moviesRepository: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MoviesRepository::class.java).newInstance(moviesRepository)
    }
}