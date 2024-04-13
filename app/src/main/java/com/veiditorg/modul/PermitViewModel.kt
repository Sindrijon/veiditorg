package com.veiditorg.modul

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veiditorg.repository.FishingPermitRepository

class PermitViewModel : ViewModel() {

    private val repository : FishingPermitRepository
    private  val _allPermits = MutableLiveData<List<Permit>>()
    val allPermits : LiveData<List<Permit>> = _allPermits

    init {
        repository = FishingPermitRepository().getInstance()
        repository.loadPermit(_allPermits)
    }

}