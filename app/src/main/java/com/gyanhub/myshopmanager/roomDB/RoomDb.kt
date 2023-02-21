package com.gyanhub.myshopmanager.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gyanhub.myshopmanager.model.MyShopModel

@Database(entities = [MyShopModel::class], version =1)
@TypeConverters(ItemsHistoryConverter::class)
abstract class RoomDb :RoomDatabase(){
    abstract fun DatabaseDao(): MyShopDao
    companion object {

        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb {
            synchronized(this) {
                if (INSTANCE == null) {
                    synchronized(this) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomDb::class.java,
                            "MyShopDb"
                        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}