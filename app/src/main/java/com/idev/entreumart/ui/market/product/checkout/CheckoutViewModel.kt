package com.idev.entreumart.ui.market.product.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.remote.request.CheckoutRequest
import com.idev.entreumart.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()
    fun checkout(token: String, id: Int, checkoutRequest: CheckoutRequest) =
        repository.checkout(token, id, checkoutRequest)
}