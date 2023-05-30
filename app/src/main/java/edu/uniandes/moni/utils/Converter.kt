package edu.uniandes.moni.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromStringArray(array: Array<String>): String {
        return Gson().toJson(array)
    }

    @TypeConverter
    fun toStringArray(value: String): Array<String> {
        val type = object : TypeToken<Array<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromIntArray(array: Array<Int>): String {
        return Gson().toJson(array)
    }

    @TypeConverter
    fun toIntArray(value: String): Array<Int> {
        val type = object : TypeToken<Array<Int>>() {}.type
        return Gson().fromJson(value, type)
    }
}