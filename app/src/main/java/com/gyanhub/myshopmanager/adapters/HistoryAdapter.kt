package com.gyanhub.myshopmanager.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gyanhub.myshopmanager.R
import com.gyanhub.myshopmanager.model.ItemsHistory
import com.gyanhub.myshopmanager.model.MyShopModel


class HistoryAdapter(
    private val itemList: List<ItemsHistory>,
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hType: TextView = view.findViewById(R.id.txtTypeH)
        val hCount: TextView = view.findViewById(R.id.txtItemCountH)
        val dateTime: TextView = view.findViewById(R.id.txtDateTime)
        val card: CardView = view.findViewById(R.id.cardOfHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_card, parent, false)
        return HistoryViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val items = itemList[position]
        holder.hType.text = items.type
        holder.hCount.text = items.itemCount.toString()
        holder.dateTime.text = items.time +" || "+ items.date
        if(items.type == "Add"){
            holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.add))
        }else{
            holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.used))
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}
