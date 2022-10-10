package com.idev.entreumart.ui.market.product.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getInvoice(token: String) = repository.getInvoice(token)
}