package com.jrodriguezva.mifilmo.di

import android.content.Context
import androidx.room.Room
import com.jrodriguezva.mifilmo.framework.local.movie.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private const val DATABASE_NAME = "movie.db"

    @Provides
    @Singleton
    fun provideLocalDataSource(db: RickAndMortyDatabase): LocalDataSource = RoomDataSource(db)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}