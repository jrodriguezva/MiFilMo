package com.jrodriguezva.mifilmo.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FirebaseRetrofit