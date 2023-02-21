package com.gyanhub.myshopmanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyanhub.myshopmanager.model.MyShopModel
import com.gyanhub.myshopmanager.repository.MyShopRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val myShopRepository: MyShopRepo):ViewModel() {

    private  val shopLiveDatas= MutableLiveData<List<MyShopModel>>()
    val data : LiveData<List<MyShopModel>>
        get() = shopLiveDatas

    fun getItem(){
        viewModelScope.launch(Dispatchers.IO){
            shopLiveDatas.postValue(myShopRepository.getMyShop())
        }
    }
    fun addShopItem(item : MyShopModel){
        viewModelScope.launch {
            myShopRepository.addItems(item)
        }
    }

}