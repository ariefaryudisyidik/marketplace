package com.excode.marketplace.ui.market.product.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.excode.marketplace.data.local.datastore.UserPreference
import com.excode.marketplace.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getWishlist(token: String) = repository.getWishlist(token)

    fun addWishList(token: String, id: Int) = repository.addWishlist(token, id)

    fun deleteWishlist(token: String, id: Int) = repository.deleteWishlist(token, id)
}