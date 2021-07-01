package com.jrodriguezva.mifilmo.domain.usecase

import com.jrodriguezva.mifilmo.domain.repository.PersonRepository
import javax.inject.Inject

class GetPerson @Inject constructor(private val repository: PersonRepository) {
    operator fun invoke(id: Int) = repository.getPersonDetails(id)
}