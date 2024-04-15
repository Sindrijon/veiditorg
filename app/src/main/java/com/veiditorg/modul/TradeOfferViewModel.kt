package com.veiditorg.modul

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.veiditorg.repository.TradeOfferRepository

class TradeOfferViewModel : ViewModel() {
    private val repository : TradeOfferRepository
    private val _allTradeOffers = MutableLiveData<List<TradeOffer>>()
    val allTradeoffers : LiveData<List<TradeOffer>> = _allTradeOffers

    init {
        repository = TradeOfferRepository().getInstance()
        repository.loadTradeOffer(_allTradeOffers)
    }
}
