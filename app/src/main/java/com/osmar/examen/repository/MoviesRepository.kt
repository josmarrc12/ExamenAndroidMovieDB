package com.osmar.examen.repository

import com.osmar.domain.models.*
import com.osmar.web.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getList(type:Int, page:Int):Flow<ListWithMovies?>
    fun updateListbyApi(type:Int, page:Int):Flow<NetworkResult<MovieList>>
    fun getMovie(movieId:Long):Flow<Movie?>
}