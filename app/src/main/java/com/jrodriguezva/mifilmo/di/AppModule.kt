package com.jrodriguezva.mifilmo.di

import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WrapperEntryPoint {
        val preferenceDataSource: PreferenceDataSource
    }
}