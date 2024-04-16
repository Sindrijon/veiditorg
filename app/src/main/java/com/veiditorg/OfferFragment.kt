package com.veiditorg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.veiditorg.adapter.HomepageAdapter
import com.veiditorg.adapter.MakeOfferAdapter
import com.veiditorg.modul.Permit
import com.veiditorg.modul.PermitViewModel
import com.veiditorg.modul.TradeOffer
import com.veiditorg.modul.TradeStatus
import java.util.UUID

class OfferFragment : Fragment(), MakeOfferAdapter.MakeOfferClickListener {

    private lateinit var permitRecyclerView: RecyclerView
    lateinit var adapter: MakeOfferAdapter
    private lateinit var viewModel: PermitViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer, container, false)
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView?.visibility = View.VISIBLE

            var auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val ownerId = currentUser?.uid ?: ""


            permitRecyclerView = view.findViewById(R.id.recyclerView2)
            permitRecyclerView.layoutManager = LinearLayoutManager(context)
            permitRecyclerView.setHasFixedSize(true)
            adapter = MakeOfferAdapter(this)
            permitRecyclerView.adapter = adapter

            viewModel = ViewModelProvider(this).get(PermitViewModel::class.java)

            viewModel.allPermits.observe(viewLifecycleOwner, Observer { allPermits ->

                val forTradePermits = allPermits.filter { permit ->
                    permit.ownerId == ownerId
                }
                Log.d("Marketplace", "Permits list updated: ${forTradePermits.size} items")

                adapter.updateOfferList(forTradePermits)
            })
        }

    override fun onMakeOfferClicked(permit: Permit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUserId = currentUser?.uid ?: ""

        //frá bundlinu úr Marketplacefragmentinu.
        val respondingriver = arguments?.getString("river")
        val respondingUserId = arguments?.getString("ownerId")
        val respondingStartDate = arguments?.getString("startDate")
        val respondingEndDate = arguments?.getString("endDate")
        val respondingPermitID = arguments?.getString("permitID")


        if (respondingriver == null || respondingStartDate == null || respondingEndDate == null || respondingPermitID == null) {
            Toast.makeText(requireContext(), "Trade information is incomplete.", Toast.LENGTH_SHORT).show()
            return
        }

        val tradeId = generatePermitID()

        val newTradeOffer = TradeOffer(
            tradeId = tradeId,
            initiatingUserId = currentUserId,
            initiatingPermitId = permit.permitID,
            respondingUserId = respondingUserId,
            respondingPermitId = respondingPermitID,
            status = TradeStatus.PENDING.name
        )

        val databaseRef = FirebaseDatabase.getInstance().getReference("tradeoffer")
        databaseRef.child(tradeId).setValue(newTradeOffer)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Trade offer made successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to make trade offer.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun generatePermitID(): String {
        return UUID.randomUUID().toString()
}
}
