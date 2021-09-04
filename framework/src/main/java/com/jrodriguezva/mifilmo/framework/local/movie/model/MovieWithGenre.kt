package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenre(
    @Embedded val movie: MovieDB,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDB>,
)