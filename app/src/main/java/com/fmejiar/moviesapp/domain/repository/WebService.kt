package com.fmejiar.moviesapp.domain.repository

import com.fmejiar.moviesapp.application.AppConstants.API_KEY_QUERY_PARAMS
import com.fmejiar.moviesapp.application.AppConstants.BASE_URL
import com.fmejiar.moviesapp.application.AppConstants.GET_UPCOMING_MOVIES_API_URL
import com.fmejiar.moviesapp.data.model.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET(GET_UPCOMING_MOVIES_API_URL)
    suspend fun getUpcomingMovies(@Query(API_KEY_QUERY_PARAMS) apiKey: String): MovieList
}

object RetrofitClient {

    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)

    }
}