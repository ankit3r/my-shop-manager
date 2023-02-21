package com.gyanhub.myshopmanager.roomDB

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gyanhub.myshopmanager.model.MyShopModel

@Dao
interface MyShopDao {

    @Insert
    suspend fun addItemsInDb(item:MyShopModel)

    @Query("SELECT * FROM MyShop")
    suspend fun getItem(): List<MyShopModel>

    @Update
    suspend fun updateItemHistory(item:MyShopModel)

}