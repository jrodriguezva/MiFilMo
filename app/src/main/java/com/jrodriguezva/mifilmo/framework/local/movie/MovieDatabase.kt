package com.jrodriguezva.mifilmo.framework.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jrodriguezva.mifilmo.framework.local.movie.model.GenreDB
import com.jrodriguezva.mifilmo.framework.local.movie.model.MovieDB
import com.jrodriguezva.mifilmo.framework.local.movie.model.MovieGenreCrossRef
import com.jrodriguezva.mifilmo.framework.local.movie.model.MoviePeopleCrossRef
import com.jrodriguezva.mifilmo.framework.local.movie.model.PeopleDB

@Database(
    entities = [
        GenreDB::class,
        MovieDB::class,
        MovieGenreCrossRef::class,
        MoviePeopleCrossRef::class,
        PeopleDB::class
    ], version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun peopleDao(): PeopleDao
    abstract fun moviePeopleDao(): MovieWithPeopleDao
}