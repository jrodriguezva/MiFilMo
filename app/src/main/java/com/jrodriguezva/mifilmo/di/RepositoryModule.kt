package com.jrodriguezva.mifilmo.di

import com.jrodriguezva.mifilmo.data.datasource.local.MovieLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.MovieNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.UserNetworkDataSource
import com.jrodriguezva.mifilmo.data.repository.AuthRepositoryImpl
import com.jrodriguezva.mifilmo.data.repository.MovieRepositoryImpl
import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        userNetworkDataSource: UserNetworkDataSource
    ): AuthRepository = AuthRepositoryImpl(userNetworkDataSource)

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieNetworkDataSource: MovieNetworkDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        dispatcher: CoroutineDispatcher,
    ): MovieRepository =
        MovieRepositoryImpl(movieNetworkDataSource, movieLocalDataSource, dispatcher)

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}