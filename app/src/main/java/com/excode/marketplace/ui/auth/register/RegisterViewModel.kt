package com.excode.marketplace.ui.auth.register

import androidx.lifecycle.ViewModel
import com.excode.marketplace.data.remote.request.RegisterRequest
import com.excode.marketplace.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun register(registerRequest: RegisterRequest) = repository.register(registerRequest)
}