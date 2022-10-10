package com.idev.entreumart.ui.market.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.remote.response.UserResponse
import com.idev.entreumart.data.repository.AuthRepository
import com.idev.entreumart.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getUser(token: String) = repository.getUser(token)

    fun updateUser(
        token: String,
        picture: File? = null,
        username: String,
        email: String,
        phoneNumber: String
    ): LiveData<Resource<UserResponse>> {
        val reqUsername = username.toRequestBody("text/plain".toMediaType())
        val reqEmail = email.toRequestBody("text/plain".toMediaType())
        val reqPhoneNumber = phoneNumber.toRequestBody("text/plain".toMediaType())
        val reqImageFile = picture?.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part? = reqImageFile?.let {
            MultipartBody.Part.createFormData(
                "picture",
                picture.name,
                it
            )
        }

        return repository.updateUser(token, imageMultipart, reqUsername, reqEmail, reqPhoneNumber)
    }

    fun clearLoginStatus() {
        viewModelScope.launch {
            pref.clearLoginStatus()
        }
    }
}