package com.idev.entreumart.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.remote.request.LoginRequest
import com.idev.entreumart.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val pref: UserPreference
) : ViewModel() {

    fun login(loginRequest: LoginRequest) = repository.login(loginRequest)

    fun saveLoginStatus(token: String, status: Boolean) {
        viewModelScope.launch {
            pref.saveLoginStatus(token, status)
        }
    }
}