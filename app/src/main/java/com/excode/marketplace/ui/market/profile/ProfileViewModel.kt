package com.excode.marketplace.ui.market.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.excode.marketplace.data.local.datastore.UserPreference
import com.excode.marketplace.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getUser(token: String) = repository.getUser(token)

    fun clearLoginStatus() {
        viewModelScope.launch {
            pref.clearLoginStatus()
        }
    }
}