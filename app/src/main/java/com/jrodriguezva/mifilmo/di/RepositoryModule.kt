package com.jrodriguezva.mifilmo.di

import com.jrodriguezva.mifilmo.data.datasource.UserNetworkDataSource
import com.jrodriguezva.mifilmo.data.repository.AuthRepositoryImpl
import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        userNetworkDataSource: UserNetworkDataSource
    ): AuthRepository = AuthRepositoryImpl(userNetworkDataSource)

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}