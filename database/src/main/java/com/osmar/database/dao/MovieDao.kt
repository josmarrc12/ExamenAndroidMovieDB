package com.osmar.database.dao


import androidx.room.*
import com.osmar.domain.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: List<Movie>)

    @Query("SELECT * FROM Movie WHERE movieId = :movieId LIMIT 1")
    suspend fun getMovie(movieId:Long):Movie?


}