package com.veiditorg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.adapter.TradeOfferAdapter
import com.veiditorg.modul.TradeOffer
import com.veiditorg.modul.TradeOfferViewModel






class TradeFragment : Fragment(), TradeOfferAdapter.TradeOfferClickListener {

    private lateinit var viewModel: TradeOfferViewModel
    private lateinit var tradeOfferRecyclerView: RecyclerView
    lateinit var adapter: TradeOfferAdapter
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trade_fragment, container, false)
    }


    private fun swapOwnerIds(tradeOffer: TradeOffer) {
        val databaseRef = FirebaseDatabase.getInstance().getReference()

        // References to the owner fields in Firebase
        val initiatingOwnerRef =
            tradeOffer.initiatingPermitId?.let { databaseRef.child("permit").child(it).child("ownerId") }
        val respondingOwnerRef =
            tradeOffer.respondingPermitId?.let { databaseRef.child("permit").child(it).child("ownerId") }

        // Temporary storage for owner IDs
        var tempOwnerId: String? = null

        // Start by getting the owner ID of the initiating permit
        if (initiatingOwnerRef != null) {
            initiatingOwnerRef.get().addOnSuccessListener { snapshot ->
                tempOwnerId = snapshot.value as? String  // Store the current initiating ownerId
                if (tempOwnerId == null) {
                    Toast.makeText(context, "Failed to find initiating owner ID", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                // Now set the initiating permit's owner ID to the responding user's ID
                initiatingOwnerRef.setValue(tradeOffer.respondingUserId)
                    .addOnSuccessListener {
                        // After successfully setting, change the responding permit's owner ID to the stored tempOwnerId
                        if (respondingOwnerRef != null) {
                            respondingOwnerRef.setValue(tempOwnerId)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Owner IDs swapped successfully.", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    // Attempt to revert changes if the second update fails
                                    initiatingOwnerRef.setValue(tempOwnerId)
                                    Toast.makeText(context, "Failed to swap owner IDs, reverted changes.", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to update initiating owner ID", Toast.LENGTH_SHORT).show()
                    }
            }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to retrieve initiating owner ID", Toast.LENGTH_SHORT).show()
                }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val ownerId = currentUser?.uid ?: ""

        tradeOfferRecyclerView = view.findViewById(R.id.TradeOfferrecyclerView)
        tradeOfferRecyclerView.layoutManager = LinearLayoutManager(context)
        tradeOfferRecyclerView.setHasFixedSize(true)
        adapter = TradeOfferAdapter(this)
        tradeOfferRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(TradeOfferViewModel::class.java)
        viewModel.allTradeoffers.observe(viewLifecycleOwner, Observer {allOffers ->
            val currentUserOffers = allOffers.filter{tradeOffer ->
                tradeOffer.respondingUserId==ownerId
            }

            adapter.updatePermitList(currentUserOffers)
        })

    }

    private fun getSelectedTradeOffer(): TradeOffer? {
        return null  // Placeholder
    }

    override fun onAcceptClicked(tradeOffer: TradeOffer) {
        // Handle what happens when an offer is accepted
        swapOwnerIds(tradeOffer)
        Toast.makeText(context, "Offer accepted", Toast.LENGTH_SHORT).show()
        val tradeId = tradeOffer.tradeId
        if(tradeId !=null) {
            val database = FirebaseDatabase.getInstance()
            val permitRef = database.getReference("tradeoffer").child(tradeId)
            permitRef.removeValue()
        }
    }

    override fun onDeclineClicked(tradeOffer: TradeOffer) {
        // Handle what happens when an offer is declined
        Toast.makeText(context, "Offer declined", Toast.LENGTH_SHORT).show()
        val tradeId = tradeOffer.tradeId
        if(tradeId !=null) {
            val database = FirebaseDatabase.getInstance()
            val permitRef = database.getReference("tradeoffer").child(tradeId)
            permitRef.removeValue()
        }
    }



}