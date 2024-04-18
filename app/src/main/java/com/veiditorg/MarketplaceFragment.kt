package com.veiditorg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.veiditorg.adapter.MyAdapter
import com.veiditorg.modul.PermitViewModel
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veiditorg.modul.Permit



class MarketplaceFragment : Fragment(), MyAdapter.TradeButtonClickListener {

    private lateinit var viewModel: PermitViewModel
    private lateinit var permitRecyclerView: RecyclerView
    lateinit var adapter: MyAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.visibility = View.VISIBLE

        val forTrade = true

        permitRecyclerView = view.findViewById(R.id.recyclerView)
        permitRecyclerView.layoutManager = LinearLayoutManager(context)
        permitRecyclerView.setHasFixedSize(true)
        adapter = MyAdapter(this)
        permitRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(PermitViewModel::class.java)

        viewModel.allPermits.observe(viewLifecycleOwner, Observer { allPermits ->

            val forTradePermits = allPermits.filter { permit ->
                permit.forTrade == forTrade
            }
            Log.d("Marketplace", "Permits list updated: ${forTradePermits.size} items")

            adapter.updatePermitList(forTradePermits)
        })
    }

    override fun onTradeButtonClick(permit: Permit) {
        val bundle = Bundle().apply {
            putString("river", permit.river)
            putString("ownerId", permit.ownerId)
            putBoolean("forTrade", permit.forTrade)
            putString("startDate", permit.startDate)
            putString("endDate", permit.endDate)
            putString("permitID", permit.permitID)
        }

        val offerFragment = OfferFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction().apply {
            replace(
                R.id.frame_layout,
                offerFragment
            )
            addToBackStack(null)
            commit()
        }
    }
}

