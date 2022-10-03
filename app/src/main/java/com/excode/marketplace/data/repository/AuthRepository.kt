package com.excode.marketplace.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.MarketApi
import com.excode.marketplace.data.remote.request.LoginRequest
import com.excode.marketplace.data.remote.request.RegisterRequest
import com.excode.marketplace.data.remote.response.AuthResponse
import com.excode.marketplace.data.remote.response.UserResponse
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.getMessage
import com.excode.marketplace.utils.getString
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val context: Context,
    private val api: MarketApi
) {
    fun login(loginRequest: LoginRequest): LiveData<Resource<AuthResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.login(loginRequest)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun register(registerRequest: RegisterRequest): LiveData<Resource<AuthResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.register(registerRequest)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun getUser(token: String): LiveData<Resource<UserResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getUser(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }
}