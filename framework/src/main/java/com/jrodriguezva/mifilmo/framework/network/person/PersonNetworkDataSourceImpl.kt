package com.jrodriguezva.mifilmo.framework.network.person

import com.jrodriguezva.mifilmo.data.datasource.network.PersonNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.Person
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.exception.BadRequest
import com.jrodriguezva.mifilmo.domain.model.exception.NoConnectionAvailable
import com.jrodriguezva.mifilmo.domain.model.exception.PersonNotFound
import com.jrodriguezva.mifilmo.framework.mapper.toDomain
import com.jrodriguezva.mifilmo.framework.network.NetworkUtils
import com.jrodriguezva.mifilmo.framework.network.movie.TMDBApi

class PersonNetworkDataSourceImpl(
    private val api: TMDBApi,
    private val apiKey: String,
    private val networkUtils: NetworkUtils
) : PersonNetworkDataSource {

    override suspend fun getPersonDetails(personId: Int, language: String): Resource<Person> {
        if (!networkUtils.isNetworkAvailable()) return Resource.Failure(NoConnectionAvailable)
        return api.getPersonDetails(personId, language, apiKey).run {
            if (isSuccessful) {
                body()?.let { body -> Resource.Success(body.toDomain()) }
                    ?: Resource.Failure(PersonNotFound)
            } else {
                Resource.Failure(BadRequest)
            }
        }
    }

}