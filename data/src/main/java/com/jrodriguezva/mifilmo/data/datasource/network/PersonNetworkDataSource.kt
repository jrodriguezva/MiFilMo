package com.jrodriguezva.mifilmo.data.datasource.network

import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.People
import com.jrodriguezva.mifilmo.domain.model.Person
import com.jrodriguezva.mifilmo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface PersonNetworkDataSource {
    suspend fun getPersonDetails(personId: Int, language: String): Resource<Person>
}
