package com.gyanhub.myshopmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyanhub.myshopmanager.repository.MyShopRepo

class MainFactory (private val myShopRepository: MyShopRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(myShopRepository) as T
    }
}