package com.jrodriguezva.mifilmo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.jrodriguezva.mifilmo.BuildConfig
import com.jrodriguezva.mifilmo.data.datasource.network.MessageNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.MovieNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.PersonNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.UserNetworkDataSource
import com.jrodriguezva.mifilmo.framework.network.NetworkUtils
import com.jrodriguezva.mifilmo.framework.network.message.MessageNetworkDataSourceImpl
import com.jrodriguezva.mifilmo.framework.network.movie.MovieNetworkDataSourceImpl
import com.jrodriguezva.mifilmo.framework.network.movie.TMDBApi
import com.jrodriguezva.mifilmo.framework.network.person.PersonNetworkDataSourceImpl
import com.jrodriguezva.mifilmo.framework.network.user.UserNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUserNetworkDataSource(
        auth: FirebaseAuth,
        storage: FirebaseStorage
    ): UserNetworkDataSource = UserNetworkDataSourceImpl(auth, storage)

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseStorage() = Firebase.storage

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        val database = Firebase.database
        database.setPersistenceEnabled(true)
        return database
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .followRedirects(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
        .client(okHttpClient)
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideMovieNetworkDataSource(
        retrofit: Retrofit,
        networkUtils: NetworkUtils,
        database: FirebaseDatabase,
        auth: FirebaseAuth,
    ): MovieNetworkDataSource =
        MovieNetworkDataSourceImpl(
            retrofit.create(TMDBApi::class.java),
            BuildConfig.API_KEY,
            database,
            auth,
            networkUtils
        )

    @Provides
    @Singleton
    fun provideMessageNetworkDataSourceImpl(
        database: FirebaseDatabase,
        auth: FirebaseAuth,
    ): MessageNetworkDataSource =
        MessageNetworkDataSourceImpl(
            database,
            auth
        )

    @Provides
    @Singleton
    fun providePersonNetworkDataSource(
        retrofit: Retrofit,
        networkUtils: NetworkUtils,
    ): PersonNetworkDataSource =
        PersonNetworkDataSourceImpl(
            retrofit.create(TMDBApi::class.java),
            BuildConfig.API_KEY,
            networkUtils
        )
}