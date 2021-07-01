package com.jrodriguezva.mifilmo.data.datasource.local

import com.jrodriguezva.mifilmo.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonLocalDataSource {
    suspend fun getPerson(personId: Int): Person?

    suspend fun insertPerson(person: Person)
}