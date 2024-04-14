package com.veiditorg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.veiditorg.adapter.MyAdapter
import com.veiditorg.modul.PermitViewModel
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketplaceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class MarketplaceFragment : Fragment() {

    private lateinit var viewModel: PermitViewModel
    private lateinit var permitRecyclerView: RecyclerView
    lateinit var adapter: MyAdapter


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarketplaceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarketplaceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        val forTrade = true

        permitRecyclerView = view.findViewById(R.id.recyclerView)
        permitRecyclerView.layoutManager = LinearLayoutManager(context)
        permitRecyclerView.setHasFixedSize(true)
        adapter = MyAdapter(false)
        permitRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(PermitViewModel::class.java)

        viewModel.allPermits.observe(viewLifecycleOwner, Observer {allPermits ->

            val forTradePermits = allPermits.filter { permit ->
                permit.forTrade == forTrade
            }
            Log.d("Marketplace", "Permits list updated: ${forTradePermits.size} items")

            adapter.updatePermitList(forTradePermits)
        })
    }
}
