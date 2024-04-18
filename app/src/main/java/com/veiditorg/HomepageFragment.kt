package com.veiditorg

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veiditorg.adapter.MyAdapter
import com.veiditorg.modul.PermitViewModel
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.veiditorg.adapter.HomepageAdapter


class HomepageFragment : Fragment() {

    private lateinit var viewModel: PermitViewModel
    private lateinit var permitRecyclerView: RecyclerView
    lateinit var adapter: HomepageAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_homepage, container, false)
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

        permitRecyclerView = view.findViewById(R.id.homepageRecyclerview)
        permitRecyclerView.layoutManager = LinearLayoutManager(context)
        permitRecyclerView.setHasFixedSize(true)
        adapter = HomepageAdapter()
        permitRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(PermitViewModel::class.java)

        viewModel.allPermits.observe(viewLifecycleOwner, Observer {allPermits ->

            val currentUserPermits = allPermits.filter { permit ->
                permit.ownerId==ownerId
            }
            Log.d("Homepage", "Permits list updated: ${currentUserPermits.size} items")

            adapter.updatePermitList(currentUserPermits)
        })
    }
}