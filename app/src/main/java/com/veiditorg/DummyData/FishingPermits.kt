package com.veiditorg.DummyData

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FishingPermits(
    private val permits: MutableList<Permit>,
    private var idCounter: Int = 1
) {
    // Contains all fishing permits in a mutable list
    class PermitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addPermit(permit: Permit) {
        permit.setID(idCounter)
        idCounter += 1
        permits.add(permit)
    }

    fun addPermits(listOfPermits: MutableList<Permit>) {
        for (permit in listOfPermits) {
            this.addPermit(permit)
        }
    }

    fun deletePermit(permit: Permit) {
        permits.remove(permit)
    }

    fun getItemCount(): Int {
        return permits.size
    }
}