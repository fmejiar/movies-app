package com.fmejiar.moviesapp.core

sealed class UpcomingMoviesResult<out T> {
    class Loading<out T>: UpcomingMoviesResult<T>()
    data class Success<out T>(val data: T): UpcomingMoviesResult<T>()
    data class Failure(val exception: Exception): UpcomingMoviesResult<Nothing>()
}