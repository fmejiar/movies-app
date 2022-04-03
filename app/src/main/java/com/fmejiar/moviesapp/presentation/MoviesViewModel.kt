package com.fmejiar.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.fmejiar.moviesapp.core.UpcomingMoviesResult
import com.fmejiar.moviesapp.domain.usecase.DoLogInUseCase
import com.fmejiar.moviesapp.domain.usecase.GetUpcomingMoviesUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MoviesViewModel(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val doLogInUseCase: DoLogInUseCase
) : ViewModel() {

    fun fetchUpcomingMovies() = liveData(Dispatchers.IO) {
        emit(UpcomingMoviesResult.Loading())
        try {
            emit(UpcomingMoviesResult.Success(getUpcomingMoviesUseCase()))
        } catch (e: Exception) {
            emit(UpcomingMoviesResult.Failure(e))
        }
    }

    suspend fun doLogin(user: String, password: String): Boolean =
        doLogInUseCase.invoke(user, password)

}

class MoviesViewModelFactory(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val doLogInUseCase: DoLogInUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetUpcomingMoviesUseCase::class.java,
            DoLogInUseCase::class.java
        ).newInstance(getUpcomingMoviesUseCase, doLogInUseCase)
    }
}