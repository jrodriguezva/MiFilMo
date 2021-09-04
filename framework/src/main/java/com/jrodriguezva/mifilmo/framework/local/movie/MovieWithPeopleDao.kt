package com.jrodriguezva.mifilmo.framework.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jrodriguezva.mifilmo.framework.local.movie.model.MoviePeopleCrossRef
import com.jrodriguezva.mifilmo.framework.local.movie.model.MovieWithPeople
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieWithPeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(join: MoviePeopleCrossRef)

    @Transaction
    fun insert(movieId: Int, peopleIds: List<Int>) {
        peopleIds.forEach {
            insert(MoviePeopleCrossRef(movieId, it))
        }
    }

    @Transaction
    @Query("SELECT * FROM Movie WHERE movieId = :id")
    suspend fun getPeoplesByMovie(id: Int): MovieWithPeople?
}