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

class MyAdapter(private val isInHomepageFragment: Boolean) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val permitList = ArrayList<Permit>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = if(isInHomepageFragment) {
            R.layout.inventory_item
        }
        else {
            R.layout.list_item
        }
        val itemView = LayoutInflater.from(parent.context).inflate(
            layout,
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
        holder.bind(currentitem)
//        holder.fortrade.text = currentitem.forTrade.toString()
//        holder.ownerid.text = currentitem.ownerId
        //holder.river.text = currentitem.river
        //holder.startdate.text = currentitem.startDate
        //holder.enddate.text = currentitem.endDate

        holder.toggleSwitch?.isChecked = currentitem.forTrade
        holder.toggleSwitch?.setOnCheckedChangeListener { _, isChecked ->
            currentitem.forTrade = isChecked

            val database = FirebaseDatabase.getInstance()
            val permitRef = database.getReference("permit").child(currentitem.permitID!!)

            permitRef.child("forTrade").setValue(currentitem.forTrade)
        }

        holder.deleteButton?.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            val permitRef =database.getReference("permit").child(currentitem.permitID!!)
            permitRef.removeValue()
        }
    }

    class  MyViewHolder(itemView : android.view.View) : RecyclerView.ViewHolder(itemView){

        val river : TextView = itemView.findViewById(R.id.tvriver)
//        val fortrade : TextView = itemView.findViewById(R.id.tilsolu)
//        val ownerid : TextView = itemView.findViewById(R.id.tvownerid)
        val startdate : TextView = itemView.findViewById(R.id.tvstartdate)
        val enddate : TextView = itemView.findViewById(R.id.tvenddate)
        val toggleSwitch: Switch? = itemView.findViewById(R.id.switch1)
        val deleteButton: FloatingActionButton?=itemView.findViewById(R.id.deletePermit)
        fun bind(permit: Permit) {
            river.text = permit.river
            startdate.text = permit.startDate
            enddate.text = permit.endDate
        }
    }
}