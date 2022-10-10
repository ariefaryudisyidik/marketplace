package com.idev.entreumart.ui.market.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.repository.AuthRepository
import com.idev.entreumart.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val marketRepository: MarketRepository,
    private val authRepository: AuthRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getProducts(token: String) = marketRepository.getProducts(token)

    fun getUser(token: String) = authRepository.getUser(token)
}