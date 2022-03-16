package com.osmar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.osmar.database.dao.MovieDao
import com.osmar.database.dao.MovieListDao
import com.osmar.domain.models.*
import com.osmar.database.converters.*

@Database(entities = [Movie::class, MovieList::class, MoviesListRef::class], version = 1)
@TypeConverters(DBTypeConverters::class)
abstract class DBMovie: RoomDatabase() {


    abstract fun movieDao():MovieDao
    abstract fun movieListDao(): MovieListDao

    companion object{

        @JvmStatic
        fun newInstance(context:Context): DBMovie {
            return Room.databaseBuilder(context,DBMovie::class.java,"DBMovie")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}