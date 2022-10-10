package com.idev.entreumart.ui.auth.register

import androidx.lifecycle.ViewModel
import com.idev.entreumart.data.remote.request.RegisterRequest
import com.idev.entreumart.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun register(registerRequest: RegisterRequest) = repository.register(registerRequest)
}