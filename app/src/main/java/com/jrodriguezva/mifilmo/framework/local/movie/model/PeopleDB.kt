package com.jrodriguezva.mifilmo.framework.local.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PeopleDB(
    @PrimaryKey val peopleId: Int,
    val name: String,
    val character: String,
    val profilePath: String?,
    val order: Int,
)
