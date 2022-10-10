package com.idev.entreumart.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.MarketApi
import com.idev.entreumart.data.remote.request.LoginRequest
import com.idev.entreumart.data.remote.request.RegisterRequest
import com.idev.entreumart.data.remote.response.AuthResponse
import com.idev.entreumart.data.remote.response.UserResponse
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.getMessage
import com.idev.entreumart.utils.getString
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    fun updateUser(
        token: String,
        picture: MultipartBody.Part? = null,
        username: RequestBody,
        email: RequestBody,
        phoneNumber: RequestBody,
    ): LiveData<Resource<UserResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.updateUser(token, picture, username, email, phoneNumber)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }
}