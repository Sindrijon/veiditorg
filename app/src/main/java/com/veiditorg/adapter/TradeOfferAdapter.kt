package com.veiditorg.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.modul.Permit
import com.veiditorg.modul.TradeOffer

class TradeOfferAdapter : RecyclerView.Adapter<TradeOfferAdapter.TradeViewHolder>() {

    private val tradeOfferList = ArrayList<TradeOffer>()
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("permit")




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeOfferAdapter.TradeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.trade_item,
            parent,false
        )
        return TradeOfferAdapter.TradeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tradeOfferList.size
    }

    fun updatePermitList(tradeOfferList: List<TradeOffer>){

        this.tradeOfferList.clear()
        this.tradeOfferList.addAll(tradeOfferList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TradeOfferAdapter.TradeViewHolder, position: Int) {
        val currentItem = tradeOfferList[position]
        val initiatingPermitId = currentItem.initiatingPermitId
        val respondingPermitId = currentItem.respondingPermitId

        // Fetch details for the initiating permit
        if (initiatingPermitId != null) {
            database.child(initiatingPermitId).get().addOnSuccessListener { snapshot ->
                Log.d("firebase", "Fetched data for initiating permit: ${snapshot.value}")
                val permitDetails = snapshot.getValue(Permit::class.java)
                if (permitDetails != null) {
                    holder.setInitiatingPermitDetails(permitDetails)
                } else {
                    Log.e("firebase", "No permit details found for initiating permit")
                    holder.river1.text = "No data"
                    holder.date1.text = "No data"
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data for initiating permit", it)
                holder.river1.text = "Error"
                holder.date1.text = "Error"
            }
        }

        // Fetch details for the responding permit
        if (respondingPermitId != null) {
            database.child(respondingPermitId).get().addOnSuccessListener { snapshot ->
                Log.d("firebase", "Fetched data for responding permit: ${snapshot.value}")
                val permitDetails = snapshot.getValue(Permit::class.java)
                if (permitDetails != null) {
                    holder.setRespondingPermitDetails(permitDetails)
                } else {
                    Log.e("firebase", "No permit details found for responding permit")
                    holder.river2.text = "No data"
                    holder.date2.text = "No data"
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data for responding permit", it)
                holder.river2.text = "Error"
                holder.date2.text = "Error"
            }
        }
    }

    class TradeViewHolder(itemView : android.view.View) : RecyclerView.ViewHolder(itemView){
        val river1: TextView = itemView.findViewById(R.id.textViewOfferedPermitLabel)
        val date1: TextView = itemView.findViewById(R.id.textViewOfferedPermit)
        val river2: TextView = itemView.findViewById(R.id.textViewRequestedPermitLabel)
        val date2: TextView = itemView.findViewById(R.id.textViewRequestedPermit)

        fun setInitiatingPermitDetails(permit: Permit) {
            river1.text = permit.river
            date1.text = "${permit.startDate} to ${permit.endDate}"
        }

        fun setRespondingPermitDetails(permit: Permit) {
            river2.text = permit.river
            date2.text = "${permit.startDate} to ${permit.endDate}"
        }
    }
}