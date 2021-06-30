package com.jrodriguezva.mifilmo.data.repository

import com.jrodriguezva.mifilmo.data.datasource.network.UserNetworkDataSource
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.domain.model.exception.LoginError
import com.jrodriguezva.mifilmo.domain.model.exception.RegisterError
import com.jrodriguezva.mifilmo.domain.model.exception.UserNotFound
import com.jrodriguezva.mifilmo.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepositoryImpl(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    AuthRepository {

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        userNetworkDataSource.getCurrentUser()?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Failure(UserNotFound))
    }.flowOn(dispatcher)


    override fun registerUser(
        email: String,
        password: String,
        name: String,
        photoUrl: String?
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        userNetworkDataSource.registerUser(email, password)?.let {
            it.photoUrl = photoUrl
            it.name = name
            userNetworkDataSource.updateUser(it)
            emit(Resource.Success(it))
        } ?: emit(Resource.Failure(RegisterError))
    }.flowOn(dispatcher)

    override suspend fun deleteUser() = userNetworkDataSource.deleteUser()
    override fun logout() {
        userNetworkDataSource.logout()
    }


    override fun login(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        userNetworkDataSource.login(email, password)?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Failure(LoginError))
    }.flowOn(dispatcher)
}