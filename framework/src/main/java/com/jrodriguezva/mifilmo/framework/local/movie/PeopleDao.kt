package com.jrodriguezva.mifilmo.framework.local.movie

import androidx.room.Dao
import com.jrodriguezva.mifilmo.framework.local.BaseDao
import com.jrodriguezva.mifilmo.framework.local.movie.model.PeopleDB

@Dao
interface PeopleDao : BaseDao<PeopleDB>