package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreDB(
    @PrimaryKey val genreId: Int,
    val name: String,
)
