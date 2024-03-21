package com.veiditorg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veiditorg.R
import com.veiditorg.DummyData.Permit
import com.google.android.material.imageview.ShapeableImageView


class MyAdapter(private val veidileyfi : ArrayList<Permit>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val riverInfo: TextView = itemView.findViewById(R.id.riverInfo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent,false)
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {

        return veidileyfi.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = veidileyfi[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.riverInfo.text = currentItem.river


    }
}