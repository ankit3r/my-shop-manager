package com.gyanhub.myshopmanager.model

data class MyShopModel(
    val id: Int,
    val itemName: String,
    val itemAva: Int,
    val totalItem: Int,
    val itemRem: Int,
    val history: List<ItemsHistory>
)
