package com.jrodriguezva.mifilmo.framework.local.person

import androidx.room.Dao
import androidx.room.Query
import com.jrodriguezva.mifilmo.framework.local.BaseDao
import com.jrodriguezva.mifilmo.framework.local.person.model.PersonDB

@Dao
interface PersonDao : BaseDao<PersonDB> {
    @Query("SELECT * FROM Person WHERE id = :id")
    fun findById(id: Int): PersonDB?
}