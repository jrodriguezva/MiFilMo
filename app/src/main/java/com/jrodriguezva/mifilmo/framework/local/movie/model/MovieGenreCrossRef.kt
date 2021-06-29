package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class MovieGenreCrossRef(
    @ColumnInfo(index = true) val movieId: Int,
    @ColumnInfo(index = true) val genreId: Int,
)
