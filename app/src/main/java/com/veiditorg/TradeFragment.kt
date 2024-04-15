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
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.adapter.TradeOfferAdapter
import com.veiditorg.modul.TradeOffer
import com.veiditorg.modul.TradeOfferViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TradeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var viewModel: TradeOfferViewModel
private lateinit var tradeOfferRecyclerView: RecyclerView
lateinit var adapter: TradeOfferAdapter

class TradeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trade_fragment, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TradeFragmetn.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TradeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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


        tradeOfferRecyclerView = view.findViewById(R.id.TradeOfferrecyclerView)
        tradeOfferRecyclerView.layoutManager = LinearLayoutManager(context)
        tradeOfferRecyclerView.setHasFixedSize(true)
        adapter = TradeOfferAdapter()
        tradeOfferRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(TradeOfferViewModel::class.java)
        viewModel.allTradeoffers.observe(viewLifecycleOwner, Observer {

            adapter.updatePermitList(it)
        })

//        val buttonAccept: Button = view.findViewById(R.id.buttonAccept)
//        buttonAccept.setOnClickListener {
//            // Here, you would need a way to retrieve the selected trade offer.
//            // This part is dependent on your application's UI logic.
//            val selectedTradeOffer = getSelectedTradeOffer()
//            if (selectedTradeOffer != null) {
//                swapOwnerIds(selectedTradeOffer)
//            } else {
//                Toast.makeText(context, "No trade offer selected or available for swapping.", Toast.LENGTH_LONG).show()
//            }
//        }


    }

    private fun getSelectedTradeOffer(): TradeOffer? {
        return null  // Placeholder
    }
}