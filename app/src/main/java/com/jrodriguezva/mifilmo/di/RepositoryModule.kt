package com.jrodriguezva.mifilmo.di

import com.jrodriguezva.mifilmo.data.datasource.local.MovieLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.local.PersonLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.MovieNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.PersonNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.UserNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import com.jrodriguezva.mifilmo.data.repository.AuthRepositoryImpl
import com.jrodriguezva.mifilmo.data.repository.MovieRepositoryImpl
import com.jrodriguezva.mifilmo.data.repository.PersonRepositoryImpl
import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import com.jrodriguezva.mifilmo.domain.repository.MovieRepository
import com.jrodriguezva.mifilmo.domain.repository.PersonRepository
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
        preferenceDataSource: PreferenceDataSource,
        dispatcher: CoroutineDispatcher,
    ): MovieRepository =
        MovieRepositoryImpl(
            movieNetworkDataSource,
            movieLocalDataSource,
            preferenceDataSource,
            dispatcher
        )

    @Singleton
    @Provides
    fun providePersonRepository(
        personNetworkDataSource: PersonNetworkDataSource,
        personLocalDataSource: PersonLocalDataSource,
        preferenceDataSource: PreferenceDataSource,
        dispatcher: CoroutineDispatcher,
    ): PersonRepository =
        PersonRepositoryImpl(
            personNetworkDataSource,
            personLocalDataSource,
            preferenceDataSource,
            dispatcher
        )

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}