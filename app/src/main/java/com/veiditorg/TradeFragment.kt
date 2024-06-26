package com.veiditorg

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.veiditorg.adapter.TradeOfferAdapter
import com.veiditorg.modul.TradeOffer
import com.veiditorg.modul.TradeOfferViewModel
import kotlin.random.Random

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

        val initiatingOwnerRef = tradeOffer.initiatingPermitId?.let { databaseRef.child("permit").child(it).child("ownerId") }
        val respondingOwnerRef = tradeOffer.respondingPermitId?.let { databaseRef.child("permit").child(it).child("ownerId") }

        var tempOwnerId: String? = null

        if (initiatingOwnerRef != null) {
            initiatingOwnerRef.get().addOnSuccessListener { snapshot ->
                tempOwnerId = snapshot.value as? String  // Store the current initiating ownerId
                if (tempOwnerId == null) {
                    Toast.makeText(context, "Failed to find initiating owner ID", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                initiatingOwnerRef.setValue(tradeOffer.respondingUserId)
                    .addOnSuccessListener {
                        if (respondingOwnerRef != null) {
                            respondingOwnerRef.setValue(tempOwnerId)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Owner IDs swapped successfully.", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
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
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE

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

/*
        //test
        val database = FirebaseDatabase.getInstance()
        val permitRef = database.getReference()
        permitRef.child("tradeoffer").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //val tradeOffer = snapshot.getValue(TradeOffer::class.java)
                //if(tradeOffer !=null && tradeOffer.respondingUserId==ownerId){
                    //showNotification("New Trade Offer", "You have a new trade offer.")
                //}
                Toast.makeText(context, "Plís virkaðu", Toast.LENGTH_SHORT).show()
            }
            private fun showNotification(title: String, message: String) {
                val context = activity ?: return
                // Create an intent to open the TradeFragment when notification is clicked

                // Build the notification
                val notification = NotificationCompat.Builder(context, "channel_id")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                    .setAutoCancel(true)
                    .build()

                // Show the notification
                NotificationManagerCompat.from(requireContext()).notify(0, notification)
            }




            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



 */
    }

    private fun getSelectedTradeOffer(): TradeOffer? {
        return null
    }

    override fun onAcceptClicked(tradeOffer: TradeOffer) {
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
        Toast.makeText(context, "Offer declined", Toast.LENGTH_SHORT).show()
        val tradeId = tradeOffer.tradeId
        if(tradeId !=null) {
            val database = FirebaseDatabase.getInstance()
            val permitRef = database.getReference("tradeoffer").child(tradeId)
            permitRef.removeValue()
        }
    }
}