package com.jrodriguezva.mifilmo.di

import android.content.Context
import androidx.room.Room
import com.jrodriguezva.mifilmo.data.datasource.local.MovieLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.local.PersonLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import com.jrodriguezva.mifilmo.framework.local.MiFilMoDatabase
import com.jrodriguezva.mifilmo.framework.local.movie.MovieLocalDataSourceImpl
import com.jrodriguezva.mifilmo.framework.local.person.PersonLocalDataSourceImpl
import com.jrodriguezva.mifilmo.framework.local.preferences.PreferenceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private const val DATABASE_NAME = "mifilmo.db"

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(db: MiFilMoDatabase): MovieLocalDataSource =
        MovieLocalDataSourceImpl(db)

    @Provides
    @Singleton
    fun providePersonLocalDataSource(db: MiFilMoDatabase): PersonLocalDataSource =
        PersonLocalDataSourceImpl(db)

    @Provides
    @Singleton
    fun providePreferenceDataSource(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSourceImpl(context)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MiFilMoDatabase {
        return Room.databaseBuilder(
            context,
            MiFilMoDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

}