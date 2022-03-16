package com.osmar.examen.repository

import com.osmar.database.DBMovie
import com.osmar.domain.models.*
import com.osmar.web.APIConstants
import com.osmar.web.ApiService
import com.osmar.web.BaseApiResponse
import com.osmar.web.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    val db: DBMovie,
    val apiService: ApiService
):MoviesRepository, BaseApiResponse(){


    override fun getList(type: Int, page: Int): Flow<ListWithMovies?> {
        return flow {
            this.emit(db.movieListDao().getList(type, page))
        }
    }


    override fun updateListbyApi(type: Int, page: Int): Flow<NetworkResult<MovieList>> {
        return safeApiCall{
            when(type){
                MovieList.TYPE_POPULAR -> apiService.getPopularMovies(page)
                MovieList.TYPE_TOP_RATE -> apiService.getTopRatedMovies(page)
                MovieList.TYPE_UNCOMMING -> apiService.getUpcomingMovies(page)
                else -> {
                    apiService.getPopularMovies(page)
                }
            }
        }.filter {
            it.data?.let { movieList ->
                //delete list
                db.movieListDao().getLists(type,page).forEach {
                    db.movieListDao().cleanList(it.listId?:0)
                    db.movieListDao().deleteList(it)
                }
                //insert new list
                db.movieListDao().insert(movieList.apply {
                    this.type = type
                })
                //fill list
                db.movieDao().insert(movieList.movies?: listOf())
                val listId = db.movieListDao().getList(type, page)?.list?.listId?:0
                val movieListRefs = movieList.movies?.map { movie ->
                    MoviesListRef(
                        listId = listId,
                        movieId =  movie.movieId
                    )
                }
                db.movieListDao().addMovies(movieListRefs?: listOf())
            }
            return@filter true
        }
    }


    override fun getMovie(movieId: Long): Flow<Movie?> {
        return flow {
            emit(db.movieDao().getMovie(movieId))
        }
    }

}