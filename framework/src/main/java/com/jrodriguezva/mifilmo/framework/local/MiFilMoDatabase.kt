package com.jrodriguezva.mifilmo.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jrodriguezva.mifilmo.framework.local.movie.MovieDao
import com.jrodriguezva.mifilmo.framework.local.movie.MovieWithPeopleDao
import com.jrodriguezva.mifilmo.framework.local.movie.PeopleDao
import com.jrodriguezva.mifilmo.framework.local.movie.model.GenreDB
import com.jrodriguezva.mifilmo.framework.local.movie.model.MovieDB
import com.jrodriguezva.mifilmo.framework.local.movie.model.MovieGenreCrossRef
import com.jrodriguezva.mifilmo.framework.local.movie.model.MoviePeopleCrossRef
import com.jrodriguezva.mifilmo.framework.local.movie.model.PeopleDB
import com.jrodriguezva.mifilmo.framework.local.person.PersonDao
import com.jrodriguezva.mifilmo.framework.local.person.model.PersonDB

@Database(
    entities = [
        GenreDB::class,
        MovieDB::class,
        MovieGenreCrossRef::class,
        MoviePeopleCrossRef::class,
        PeopleDB::class,
        PersonDB::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class MiFilMoDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun peopleDao(): PeopleDao
    abstract fun moviePeopleDao(): MovieWithPeopleDao
    abstract fun personDao(): PersonDao
}