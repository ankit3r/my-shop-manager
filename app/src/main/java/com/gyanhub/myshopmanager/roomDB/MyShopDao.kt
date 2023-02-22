package com.gyanhub.myshopmanager.roomDB


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
    fun updateItemHistory(item:MyShopModel)


    @Query("SELECT * FROM MyShop WHERE id = :itemId")
    fun getItemById(itemId: Int): MyShopModel


}