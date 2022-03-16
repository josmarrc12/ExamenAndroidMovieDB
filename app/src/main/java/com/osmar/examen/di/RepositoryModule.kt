package com.osmar.examen.di


import com.osmar.examen.repository.LocationRepository
import com.osmar.examen.repository.LocationRepositoryImpl
import com.osmar.examen.repository.MoviesRepository
import com.osmar.examen.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository



}