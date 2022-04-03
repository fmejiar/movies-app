package com.fmejiar.moviesapp.core

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.fmejiar.moviesapp.data.model.Movie
import com.fmejiar.moviesapp.data.model.MovieEntity
import com.fmejiar.moviesapp.data.model.MovieList

fun String.isValidEmail(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun List<MovieEntity>.toMovieList(): MovieList = MovieList(
    this.map {
        it.toMovie()
    }
)

fun MovieEntity.toMovie(): Movie = Movie(
    this.id,
    this.adult,
    this.backdrop_path ?: "",
    this.original_title,
    this.original_language,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count
)

fun Movie.toMovieEntity(): MovieEntity = MovieEntity(
    this.id,
    this.adult,
    this.backdrop_path ?: "",
    this.original_title,
    this.original_language,
    this.overview,
    this.popularity,
    this.poster_path,
    this.release_date,
    this.title,
    this.video,
    this.vote_average,
    this.vote_count
)