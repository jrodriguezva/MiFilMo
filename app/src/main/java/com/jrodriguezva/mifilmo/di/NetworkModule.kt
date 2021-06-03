package com.jrodriguezva.mifilmo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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
    fun provideUserNetworkDataSource(
        auth: FirebaseAuth,
        storage: FirebaseStorage
    ): UserNetworkDataSource =
        UserNetworkDataSourceImpl(auth, storage)

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseStorage() = Firebase.storage
}