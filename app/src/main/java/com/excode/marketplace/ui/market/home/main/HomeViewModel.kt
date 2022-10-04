package com.excode.marketplace.ui.market.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.excode.marketplace.data.local.datastore.UserPreference
import com.excode.marketplace.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MarketRepository,
    private val pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getProducts(token: String) = repository.getProducts(token)
}