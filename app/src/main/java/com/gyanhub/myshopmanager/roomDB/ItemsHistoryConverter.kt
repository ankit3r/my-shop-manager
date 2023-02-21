package com.gyanhub.myshopmanager.roomDB

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gyanhub.myshopmanager.model.ItemsHistory

object ItemsHistoryConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<ItemsHistory> {
        val listType = object : TypeToken<List<ItemsHistory>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<ItemsHistory>): String {
        return gson.toJson(list)
    }
}