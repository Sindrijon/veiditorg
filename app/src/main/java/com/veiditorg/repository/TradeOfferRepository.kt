package com.veiditorg.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.veiditorg.modul.Permit
import com.veiditorg.modul.TradeOffer
import java.lang.Exception

class TradeOfferRepository {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("tradeoffer")

    @Volatile private var INSTANCE : TradeOfferRepository ?= null

    fun getInstance() : TradeOfferRepository {
        return INSTANCE ?: synchronized(this){

            val instance = TradeOfferRepository()
            INSTANCE = instance
            instance
        }
    }
    fun loadTradeOffer(tradeOfferList : MutableLiveData<List<TradeOffer>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _tradeOfferList : List<TradeOffer> =snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(TradeOffer::class.java)!!

                    }

                    tradeOfferList.postValue(_tradeOfferList)

                }catch (e : Exception){
                    Log.e("TradeOfferRepository", "Error loading trades", e)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseConnection", "Failed to read value.", error.toException())
            }
        })

    }

}