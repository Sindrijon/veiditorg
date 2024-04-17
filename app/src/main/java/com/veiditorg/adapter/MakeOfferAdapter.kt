package com.veiditorg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.veiditorg.modul.Permit

class MakeOfferAdapter(private val makeOfferClickListener: MakeOfferClickListener) : RecyclerView.Adapter<MakeOfferAdapter.MakeOfferViewHolder>() {

    private val offerList = ArrayList<Permit>()

    interface MakeOfferClickListener {
        fun onMakeOfferClicked(permit: Permit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeOfferViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.offer_item, parent, false)
        return MakeOfferViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return offerList.size
    }
    fun updateOfferList(offerList: List<Permit>) {
        this.offerList.clear()
        this.offerList.addAll(offerList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MakeOfferViewHolder, position: Int) {
        val currentItem = offerList[position]
        holder.bind(currentItem, makeOfferClickListener)
    }

    class MakeOfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val river: TextView = itemView.findViewById(R.id.tvriverOffer)
        private val startDate: TextView = itemView.findViewById(R.id.tvstartdateOffer)
        private val endDate: TextView = itemView.findViewById(R.id.tvenddateOffer)
        private val makeOfferButton: FloatingActionButton = itemView.findViewById(R.id.makeatradeOffer)

        fun bind(permit: Permit, listener: MakeOfferClickListener) {
            river.text = permit.river
            startDate.text = permit.startDate
            endDate.text = permit.endDate
            makeOfferButton.setOnClickListener {
                listener.onMakeOfferClicked(permit)
            }
        }
    }
}