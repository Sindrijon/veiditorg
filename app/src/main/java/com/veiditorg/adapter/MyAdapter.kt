package com.veiditorg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.veiditorg.modul.Permit

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val permitList = ArrayList<Permit>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return permitList.size
    }

    fun updatePermitList(permitList: List<Permit>){

        this.permitList.clear()
        this.permitList.addAll(permitList)
        notifyDataSetChanged()

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = permitList[position]

        holder.fortrade.text = currentitem.forTrade
        holder.ownerid.text = currentitem.ownerId
        holder.river.text = currentitem.river
    }

    class  MyViewHolder(itemView : android.view.View) : RecyclerView.ViewHolder(itemView){

        val river : TextView = itemView.findViewById(R.id.tvriver)
        val fortrade : TextView = itemView.findViewById(R.id.tilsolu)
        val ownerid : TextView = itemView.findViewById(R.id.tvownerid)

    }


}