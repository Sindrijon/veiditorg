package com.veiditorg

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomepageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomepageFragment : Fragment() {

    private lateinit var viewModel: PermitViewModel
    private lateinit var permitRecyclerView: RecyclerView
    lateinit var adapter: HomepageAdapter
    private lateinit var auth: FirebaseAuth

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
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomepageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomepageFragment().apply {
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