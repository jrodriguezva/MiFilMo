package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithPeople(
    @Embedded val movie: MovieDB,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "peopleId",
        associateBy = Junction(MoviePeopleCrossRef::class)
    )
    var people: List<PeopleDB>,
)