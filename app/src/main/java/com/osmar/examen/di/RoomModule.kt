package com.osmar.examen.di

import android.content.Context
import com.osmar.database.DBMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext
        context: Context
    ): DBMovie {
        return DBMovie.newInstance(context)
    }
}