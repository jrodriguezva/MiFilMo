package com.jrodriguezva.mifilmo.framework.local.person.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "person")
data class PersonDB(
    @PrimaryKey val id: Int,
    val adult: Boolean?,
    val alsoKnownAs: List<String?>?,
    val biography: String?,
    val birthday: Date?,
    val gender: Int?,
    val homepage: String?,
    val knownForDepartment: String?,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Double?,
    val profilePath: String?
)
