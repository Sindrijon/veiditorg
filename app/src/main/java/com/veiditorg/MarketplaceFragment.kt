package com.veiditorg

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.veiditorg.DummyData.Permit
import java.time.LocalDate

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MarketplaceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var permitArrayList: ArrayList<Permit>
    private lateinit var imageId: Array<Int>
    private lateinit var veidistadur: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView and Adapter here
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        // Bara fyrir test.
        permitArrayList = ArrayList()
        for (i in imageId.indices) {
            val permit = Permit(titleImage = imageId[i], river = veidistadur[i], ownerID = "SampleOwner", startDate = LocalDate.parse("2024-6-14"), endDate = LocalDate.parse("2024-6-21"))
            permitArrayList.add(permit)
        }

        recyclerView.adapter = MyAdapter(permitArrayList)
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
            R.drawable.b,
            R.drawable.a,
            R.drawable.b
        )

        veidistadur = arrayOf(
            "Elliðaá",
            "Laxá í Mjódd",
            "Elliðaá",
            "Laxá í Mjódd",
            "Elliðaá",
            "Laxá í Mjódd",
            "Elliðaá",
            "Laxá í Mjódd",
            "Elliðaá",
            "Laxá í Mjódd"
        )
    }

}

