package com.gyanhub.myshopmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gyanhub.myshopmanager.R
import com.gyanhub.myshopmanager.model.MyShopModel

class MainActivityAdapter(
    private val context: Context,
    private val itemList: ArrayList<MyShopModel>
) : RecyclerView.Adapter<MainActivityAdapter.MainViewHolder>() {
    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
      return itemList.size
    }
}