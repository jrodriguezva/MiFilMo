package com.jrodriguezva.mifilmo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jrodriguezva.mifilmo.data.datasource.UserNetworkDataSource
import com.jrodriguezva.mifilmo.framework.network.UserNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserNetworkDataSource(auth: FirebaseAuth): UserNetworkDataSource =
        UserNetworkDataSourceImpl(auth)

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth
}