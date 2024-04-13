package com.veiditorg.DummyData

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FishingPermits(
    private val permits: MutableList<Permit>
) {
    // Contains all fishing permits in a mutable list
    class PermitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var idCounter: Int = 1

    fun addPermit(permit: Permit) {
        permit.setID(idCounter)
        idCounter += 1
        permits.add(permit)
    }

    fun deletePermit(permit: Permit) {
        permits.remove(permit)
    }
}