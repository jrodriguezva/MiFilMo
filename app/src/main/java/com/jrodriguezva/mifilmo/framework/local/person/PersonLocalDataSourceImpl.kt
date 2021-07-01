package com.jrodriguezva.mifilmo.framework.local.person

import com.jrodriguezva.mifilmo.data.datasource.local.PersonLocalDataSource
import com.jrodriguezva.mifilmo.domain.model.Person
import com.jrodriguezva.mifilmo.framework.local.MiFilMoDatabase
import com.jrodriguezva.mifilmo.framework.mapper.toDatabase
import com.jrodriguezva.mifilmo.framework.mapper.toDomain

class PersonLocalDataSourceImpl(db: MiFilMoDatabase) : PersonLocalDataSource {
    private val personDao = db.personDao()
    override suspend fun getPerson(personId: Int): Person? {
        return personDao.findById(personId)?.toDomain()
    }

    override suspend fun insertPerson(person: Person) {
        personDao.insert(person.toDatabase())
    }

}