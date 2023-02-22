package com.gyanhub.myshopmanager.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gyanhub.myshopmanager.model.MyShopModel
import com.gyanhub.myshopmanager.roomDB.MyShopDao
import com.gyanhub.myshopmanager.utils.NetworkResult
import java.util.ArrayList

class MyShopRepo(private val dao: MyShopDao) {


    suspend fun addItems(item:MyShopModel) : NetworkResult<Unit>{
        return try {
            dao.addItemsInDb(item)
            NetworkResult.Success(Unit)
        } catch (e: SQLiteConstraintException) {
            Log.e("MyRepository", "Error inserting entity: ${e.message}")
            NetworkResult.Error("Entity already exists")
        }

    }
    suspend fun getMyShop(): List<MyShopModel>{
       return dao.getItem()
    }

     fun getItemById(id:Int): MyShopModel{
       return dao.getItemById(id)
    }

   fun updateShopItem(item:MyShopModel){
        dao.updateItemHistory(item)
    }

}