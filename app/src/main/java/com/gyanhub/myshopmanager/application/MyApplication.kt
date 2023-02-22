package com.gyanhub.myshopmanager.application

import android.app.Application
import com.gyanhub.myshopmanager.repository.MyShopRepo
import com.gyanhub.myshopmanager.roomDB.RoomDb

class MyApplication:Application() {
    lateinit var MyShopRepository: MyShopRepo
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val dao = RoomDb.getDatabase(applicationContext).DatabaseDao()
        MyShopRepository = MyShopRepo(dao)
    }
}