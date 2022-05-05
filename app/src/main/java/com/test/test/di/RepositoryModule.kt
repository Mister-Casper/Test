package com.test.test.di

import com.test.test.data.repository.MoviesRepositoryImpl
import com.test.test.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl):MoviesRepository
}