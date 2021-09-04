package com.jrodriguezva.mifilmo.framework.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.Date


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(items: List<String>?): String? {
        if (items == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(items)
    }
}