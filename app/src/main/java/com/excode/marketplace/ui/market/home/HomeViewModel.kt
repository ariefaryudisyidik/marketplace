package com.excode.marketplace.ui.market.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.excode.marketplace.data.local.datastore.UserPreference
import com.excode.marketplace.data.repository.AuthRepository
import com.excode.marketplace.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val marketRepository: MarketRepository,
    private val authRepository: AuthRepository,
    private val pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getProducts(token: String) = marketRepository.getProducts(token)

    fun getUser(token: String) = authRepository.getUser(token)
}