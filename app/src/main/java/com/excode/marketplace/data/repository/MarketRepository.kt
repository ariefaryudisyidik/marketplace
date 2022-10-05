package com.excode.marketplace.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.MarketApi
import com.excode.marketplace.data.remote.response.CartResponse
import com.excode.marketplace.data.remote.response.MarketResponse
import com.excode.marketplace.data.remote.response.WishlistResponse
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.getMessage
import com.excode.marketplace.utils.getString
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MarketRepository @Inject constructor(
    private val context: Context,
    private val api: MarketApi
) {

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

    fun addWishlist(token: String, id: Int): LiveData<Resource<WishlistResponse>> = liveData {
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
}