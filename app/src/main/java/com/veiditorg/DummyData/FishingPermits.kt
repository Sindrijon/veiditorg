package com.veiditorg.DummyData

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FishingPermits(
    private val permits: MutableList<Permit>
) {
    class PermitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // This class is unfinished but it is supposed to connect to the xml file for the permits
    // and it will hold all permits in a mutable list.
    fun addPermit(permit: Permit) {
        permits.add(permit)
    }

    fun deletePermit(permit: Permit) {
        permits.remove(permit)
    }

    fun getItemCount(): Int {
        return permits.size
    }
}