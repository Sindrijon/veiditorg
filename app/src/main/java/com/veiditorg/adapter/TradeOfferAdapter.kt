package com.veiditorg.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.modul.Permit
import com.veiditorg.modul.TradeOffer

class TradeOfferAdapter(private val listener: TradeOfferClickListener) : RecyclerView.Adapter<TradeOfferAdapter.TradeViewHolder>() {

    private val tradeOfferList = ArrayList<TradeOffer>()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("permit")

    interface TradeOfferClickListener {
        fun onAcceptClicked(tradeOffer: TradeOffer)
        fun onDeclineClicked(tradeOffer: TradeOffer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trade_item, parent, false)
        return TradeViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = tradeOfferList.size

    fun updatePermitList(tradeOfferList: List<TradeOffer>) {
        this.tradeOfferList.clear()
        this.tradeOfferList.addAll(tradeOfferList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TradeViewHolder, position: Int) {
        val currentItem = tradeOfferList[position]
        holder.bind(currentItem, database)
    }

    class TradeViewHolder(itemView: View, private val listener: TradeOfferClickListener) : RecyclerView.ViewHolder(itemView) {
        private val river1: TextView = itemView.findViewById(R.id.textViewOfferedPermitLabel)
        private val date1: TextView = itemView.findViewById(R.id.textViewOfferedPermit)
        private val river2: TextView = itemView.findViewById(R.id.textViewRequestedPermitLabel)
        private val date2: TextView = itemView.findViewById(R.id.textViewRequestedPermit)
        private val acceptButton: Button = itemView.findViewById(R.id.buttonAccept)
        private val declineButton: Button = itemView.findViewById(R.id.buttonDecline)

        fun bind(tradeOffer: TradeOffer, database: DatabaseReference) {
            val initiatingPermitId = tradeOffer.initiatingPermitId
            val respondingPermitId = tradeOffer.respondingPermitId

            fetchPermitDetails(initiatingPermitId, river1, date1, database)
            fetchPermitDetails(respondingPermitId, river2, date2, database)

            acceptButton.setOnClickListener { if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onAcceptClicked(tradeOffer)

            }}
            declineButton.setOnClickListener { if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onDeclineClicked(tradeOffer)
            }}
        }

        private fun fetchPermitDetails(permitId: String?, riverView: TextView, dateView: TextView, database: DatabaseReference) {
            permitId?.let {
                database.child(it).get().addOnSuccessListener { snapshot ->
                    snapshot.getValue(Permit::class.java)?.let { permit ->
                        riverView.text = permit.river
                        dateView.text = "${permit.startDate} to ${permit.endDate}"
                    } ?: run {
                        riverView.text = "No data"
                        dateView.text = "No data"
                    }
                }.addOnFailureListener {
                    riverView.text = "Error"
                    dateView.text = "Error"
                }
            }
        }
    }
}