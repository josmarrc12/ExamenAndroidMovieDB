package com.osmar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.osmar.domain.models.*

@Dao
interface MovieListDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieList: MovieList)


    @Transaction
    @Query("SELECT * FROM MovieList WHERE type = :type AND page = :page LIMIT 1")
    suspend fun getList(type:Int, page:Int):ListWithMovies?


    @Query("SELECT * FROM MovieList WHERE type = :type AND page = :page")
    suspend fun getLists(type:Int, page:Int):List<MovieList>

    @Query("DELETE FROM MoviesListRef WHERE listId == :listId")
    suspend fun cleanList(listId:Long)


    @Query("DELETE FROM MovieList WHERE type == :type AND page == :page")
    suspend fun deleteList(type:Int, page: Int)


    @Delete
    suspend fun deleteList(movieList: MovieList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(moviesListRef: List<MoviesListRef>)

}