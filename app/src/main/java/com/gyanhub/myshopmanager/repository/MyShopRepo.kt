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


     fun addItems(item:MyShopModel){
       try {
            dao.addItemsInDb(item)
        } catch (e: SQLiteConstraintException) {
            Log.d("ANKIT","Error $e")
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