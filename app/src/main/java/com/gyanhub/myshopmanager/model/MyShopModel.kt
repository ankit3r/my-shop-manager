package com.gyanhub.myshopmanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("MyShop")
data class MyShopModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemName: String,
    val itemAva: Int,
    val totalItem: Int,
    val itemUsed: Int,
    val history: List<ItemsHistory>
)
