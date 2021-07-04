package com.jrodriguezva.mifilmo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.jrodriguezva.mifilmo.BuildConfig
import com.jrodriguezva.mifilmo.data.datasource.network.MessageNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.MovieNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.PersonNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.UserNetworkDataSource
import com.jrodriguezva.mifilmo.di.qualifier.AuthInterceptorOkHttpClient
import com.jrodriguezva.mifilmo.di.qualifier.FirebaseRetrofit
import com.jrodriguezva.mifilmo.di.qualifier.MovieRetrofit
import com.jrodriguezva.mifilmo.framework.network.NetworkUtils
import com.jrodriguezva.mifilmo.framework.network.message.FCMApi
import com.jrodriguezva.mifilmo.framework.network.message.MessageNetworkDataSourceImpl
import com.jrodriguezva.mifilmo.framework.network.movie.MovieNetworkDataSourceImpl
import com.jrodriguezva.mifilmo.framework.network.movie.TMDBApi
import com.jrodriguezva.mifilmo.framework.network.person.PersonNetworkDataSourceImpl
import com.jrodriguezva.mifilmo.framework.network.user.UserNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
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
    fun provideFirebaseMessage(): FirebaseMessaging = Firebase.messaging

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        val database = Firebase.database
        database.setPersistenceEnabled(true)
        return database
    }

    @Singleton
    @Provides
    @AuthInterceptorOkHttpClient
    fun provideAuthOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(tokenCaptorInterceptor())
            .followRedirects(false)
            .build()
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

    private fun tokenCaptorInterceptor() = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")

        request.addHeader("Authorization", "key=${BuildConfig.SERVER_KEY}")

        chain.proceed(request.build())
    }


    @Provides
    @Singleton
    @MovieRetrofit
    fun provideMovieRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
        .client(okHttpClient)
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()

    @Provides
    @Singleton
    @FirebaseRetrofit
    fun provideFirebaseRetrofit(@AuthInterceptorOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_FCM_BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideMovieNetworkDataSource(
        @MovieRetrofit retrofit: Retrofit,
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
        @FirebaseRetrofit retrofit: Retrofit,
        database: FirebaseDatabase,
        auth: FirebaseAuth,
        messaging: FirebaseMessaging,
        networkUtils: NetworkUtils
    ): MessageNetworkDataSource =
        MessageNetworkDataSourceImpl(
            retrofit.create(FCMApi::class.java),
            database,
            auth,
            messaging,
            networkUtils
        )

    @Provides
    @Singleton
    fun providePersonNetworkDataSource(
        @MovieRetrofit retrofit: Retrofit,
        networkUtils: NetworkUtils,
    ): PersonNetworkDataSource =
        PersonNetworkDataSourceImpl(
            retrofit.create(TMDBApi::class.java),
            BuildConfig.API_KEY,
            networkUtils
        )
}