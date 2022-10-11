package com.idev.entreumart.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.MarketApi
import com.idev.entreumart.data.remote.request.CheckoutRequest
import com.idev.entreumart.data.remote.response.*
import com.idev.entreumart.data.remote.response.model.Item
import com.idev.entreumart.utils.Resource
import com.idev.entreumart.utils.getMessage
import com.idev.entreumart.utils.getString
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MarketRepository @Inject constructor(
    private val context: Context,
    private val api: MarketApi
) {

    fun searchProduct(productName: String): LiveData<Resource<List<Item>>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.searchProduct(productName).data
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }


    fun getProducts(token: String): LiveData<Resource<MarketResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getProducts(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun getCarts(token: String): LiveData<Resource<CartResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getCarts(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun getWishlist(token: String): LiveData<Resource<WishlistResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getWishlist(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun addCart(token: String, id: Int): LiveData<Resource<CartPostResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.addCart(token, id)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun addWishlist(token: String, id: Int): LiveData<Resource<WishlistPostResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.addWishlist(token, id)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun deleteCart(token: String, id: Int): LiveData<Resource<CartPostResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.deleteCart(token, id)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun deleteWishlist(token: String, id: Int): LiveData<Resource<WishlistResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.deleteWishlist(token, id)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun checkout(
        token: String,
        id: Int,
        checkoutRequest: CheckoutRequest
    ): LiveData<Resource<CheckoutResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.checkout(token, id, checkoutRequest)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun getInvoice(token: String): LiveData<Resource<InvoiceResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getInvoice(token)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }

    fun uploadPayment(
        token: String,
        invoiceId: Int,
        uploadPayment: MultipartBody.Part? = null
    ): LiveData<Resource<UploadPaymentResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.uploadPayment(token, invoiceId, uploadPayment)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            val message = getMessage(e)
            emit(Resource.Error(message))
        } catch (e: IOException) {
            emit(Resource.Error(getString(context, R.string.error_connection)))
        }
    }
}