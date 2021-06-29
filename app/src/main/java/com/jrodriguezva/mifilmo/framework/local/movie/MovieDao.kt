package com.jrodriguezva.mifilmo.framework.local.movie

import androidx.room.Dao
import androidx.room.Query
import com.jrodriguezva.mifilmo.framework.local.BaseDao
import com.jrodriguezva.mifilmo.framework.local.movie.model.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : BaseDao<MovieDB> {

    @Query("SELECT * FROM Movie ORDER BY page")
    fun getAll(): Flow<List<MovieDB>>

    @Query("SELECT * FROM Movie WHERE movieId = :id")
    fun findById(id: Int): Flow<MovieDB>

    @Query("SELECT * FROM Movie WHERE movieId = :id")
    suspend fun getById(id: Int): MovieDB

    @Query("SELECT IFNULL(MAX(page),0) FROM Movie")
    suspend fun getLastPage(): Int
}