package com.jrodriguezva.mifilmo.data.repository

import com.jrodriguezva.mifilmo.data.datasource.local.PersonLocalDataSource
import com.jrodriguezva.mifilmo.data.datasource.network.PersonNetworkDataSource
import com.jrodriguezva.mifilmo.data.datasource.preferences.PreferenceDataSource
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.exception.EmptyCache
import com.jrodriguezva.mifilmo.domain.repository.PersonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PersonRepositoryImpl(
    private val personNetworkDataSource: PersonNetworkDataSource,
    private val personLocalDataSource: PersonLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PersonRepository {

    override fun getPersonDetails(personId: Int) = flow {
        emit(Resource.Loading)
        when (val result =
            personNetworkDataSource.getPersonDetails(
                personId,
                preferenceDataSource.getLanguage()
            )) {
            is Resource.Success -> {
                personLocalDataSource.insertPerson(result.data)
                emit(result)
            }
            else -> {
                when (val person = personLocalDataSource.getPerson(personId)) {
                    null -> emit(Resource.Failure(EmptyCache))
                    else -> emit(Resource.Success(person))
                }
            }
        }
    }.flowOn(dispatcher)
}
