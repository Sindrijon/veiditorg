package com.veiditorg.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.veiditorg.modul.Permit
import java.lang.Exception

class FishingPermitRepository {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("permit")

    @Volatile private var INSTANCE : FishingPermitRepository ?= null

    fun getInstance() : FishingPermitRepository {
        return INSTANCE ?: synchronized(this){

            val instance = FishingPermitRepository()
            INSTANCE = instance
            instance
        }
    }


    fun loadPermit(permitList : MutableLiveData<List<Permit>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _permitList : List<Permit> =snapshot.children.map {dataSnapshot ->

                        dataSnapshot.getValue(Permit::class.java)!!

                    }

                    permitList.postValue(_permitList)

                }catch (e : Exception){
                    Log.e("FishingPermitRepository", "Error loading permits", e)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseConnection", "Failed to read value.", error.toException())
            }
        })

    }

}