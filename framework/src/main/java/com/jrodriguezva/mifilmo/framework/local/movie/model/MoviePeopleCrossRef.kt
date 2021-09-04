package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "peopleId"])
data class MoviePeopleCrossRef(
    @ColumnInfo(index = true) val movieId: Int,
    @ColumnInfo(index = true) val peopleId: Int,
)
