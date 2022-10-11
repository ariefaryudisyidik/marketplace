package com.idev.entreumart.ui.market.product.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.remote.response.UploadPaymentResponse
import com.idev.entreumart.data.repository.MarketRepository
import com.idev.entreumart.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailShoppingViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun uploadPayment(
        token: String,
        invoiceId: Int,
        uploadPayment: File? = null
    ): LiveData<Resource<UploadPaymentResponse>> {
        val reqImageFile = uploadPayment?.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part? = reqImageFile?.let {
            MultipartBody.Part.createFormData(
                "payment_upload",
                uploadPayment.name,
                it
            )
        }
        return repository.uploadPayment(token, invoiceId, imageMultipart)
    }
}