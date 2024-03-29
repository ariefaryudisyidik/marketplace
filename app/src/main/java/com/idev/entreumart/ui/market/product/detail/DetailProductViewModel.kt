package com.idev.entreumart.ui.market.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()
    fun getWishlist(token: String) = repository.getWishlist(token)
    fun addWishlist(token: String, id: Int) = repository.addWishlist(token, id)
    fun deleteWishlist(token: String, id: Int) = repository.deleteWishlist(token, id)

    fun addCart(token: String, id: Int) = repository.addCart(token, id)
}