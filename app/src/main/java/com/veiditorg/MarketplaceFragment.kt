package com.veiditorg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.veiditorg.DummyData.Permit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MarketplaceFragment : Fragment() {

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var newArrayList: ArrayList<Permit>
    lateinit var imageId : Array<Int>
    lateinit var veidistadur : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize your views and data here after the view has been created
        // For example:
        newRecyclerview = view.findViewById(R.id.recyclerView)
        // Initialize other views and set up your RecyclerView here
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageId = arrayOf(
            R.drawable.a,
            R.drawable.b,
            R.drawable.a,
            R.drawable.b,
            R.drawable.a,
            R.drawable.b,
            R.drawable.a,
            R.drawable.b
        )

        veidistadur = arrayOf(
            "Elliðaá",
            "laxá í Mjódd,",
            "Elliðaá",
            "laxá í Mjódd,",
            "Elliðaá",
            "laxá í Mjódd,",
            "Elliðaá",
            "laxá í Mjódd,"
        )
    }

//    newRecylerview =findViewById(R.id.recyclerView)
//    newRecylerview.layoutManager = LinearLayoutManager(this)
//    newRecylerview.setHasFixedSize(true)
//
//
//    newArrayList = arrayListOf<Permit>()
//    tempArrayList = arrayListOf<Permit>()
//    getUserdata()
//
//    private fun getUserdata() {
//
//        for(i in imageId.indices){
//
//            val news = Permit(imageId[i],veidistadur[i])
//            newArrayList.add(news)
//
//        }
}


