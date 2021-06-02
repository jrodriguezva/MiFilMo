package com.jrodriguezva.mifilmo.data.repository

import com.jrodriguezva.mifilmo.data.datasource.UserNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.domain.model.exception.LoginError
import com.jrodriguezva.mifilmo.domain.model.exception.RegisterError
import com.jrodriguezva.mifilmo.domain.model.exception.UserNotFound
import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(private val userNetworkDataSource: UserNetworkDataSource) :
    AuthRepository {

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        userNetworkDataSource.getCurrentUser()?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Failure(UserNotFound))
    }


    override fun registerUser(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        userNetworkDataSource.registerUser(email, password)?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Failure(RegisterError))
    }

    override fun login(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        userNetworkDataSource.login(email, password)?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Failure(LoginError))
    }
}