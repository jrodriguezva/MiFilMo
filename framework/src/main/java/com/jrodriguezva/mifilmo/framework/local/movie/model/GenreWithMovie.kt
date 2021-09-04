package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GenreWithMovie(
    @Embedded val genre: GenreDB,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<MovieDB>,
)