package com.veiditorg.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.modul.Permit

class MyAdapter(private val tradeButtonClickListener: TradeButtonClickListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface TradeButtonClickListener {
        fun onTradeButtonClick(permit: Permit)
    }

    private val permitList = ArrayList<Permit>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return permitList.size
    }

    fun updatePermitList(permitList: List<Permit>) {

        this.permitList.clear()
        this.permitList.addAll(permitList)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = permitList[position]
        holder.bind(currentitem, tradeButtonClickListener)
    }

    class MyViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val tradeButton: FloatingActionButton = itemView.findViewById(R.id.makeatrade)
        val river: TextView = itemView.findViewById(R.id.tvriver)

        //        val fortrade : TextView = itemView.findViewById(R.id.tilsolu)
//        val ownerid : TextView = itemView.findViewById(R.id.tvownerid)
        val startdate: TextView = itemView.findViewById(R.id.tvstartdate)
        val enddate: TextView = itemView.findViewById(R.id.tvenddate)






        fun bind(permit: Permit,listener: TradeButtonClickListener) {
            river.text = permit.river
            startdate.text = permit.startDate
            enddate.text = permit.endDate
            tradeButton.setOnClickListener {
                listener.onTradeButtonClick(permit)
            }
        }
    }
}