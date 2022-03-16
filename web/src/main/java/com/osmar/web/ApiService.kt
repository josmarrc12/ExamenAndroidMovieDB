package com.osmar.web


import com.osmar.domain.models.*
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Locale.getDefault().toLanguageTag(),
        @Query("api_key") apiKey: String = APIConstants.API_KEY
    ):Response<MovieList>


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Locale.getDefault().toLanguageTag(),
        @Query("api_key") apiKey: String = APIConstants.API_KEY
    ):Response<MovieList>


    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Locale.getDefault().toLanguageTag(),
        @Query("api_key") apiKey: String = APIConstants.API_KEY
    ):Response<MovieList>


}