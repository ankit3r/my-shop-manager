package com.gyanhub.myshopmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gyanhub.myshopmanager.R
import com.gyanhub.myshopmanager.model.MyShopModel

class MainActivityAdapter(
    private val context: Context,
    private val itemList: List<MyShopModel>,
    private val itemClick:ItemClick
) : RecyclerView.Adapter<MainActivityAdapter.MainViewHolder>() {

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName:TextView = view.findViewById(R.id.txtItemName)
        val itemAva:TextView = view.findViewById(R.id.txtItemAva)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text= item.itemName
        holder.itemAva.text= context.getString(R.string.ava_item,item.itemAva.toString())
        holder.itemView.setOnClickListener { itemClick.onItemClick(item) }
    }

    override fun getItemCount(): Int {
      return itemList.size
    }

}
interface ItemClick{
    fun onItemClick(item:MyShopModel)
}