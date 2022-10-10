package com.idev.entreumart.ui.market.product.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import com.idev.entreumart.data.remote.response.model.Item
import com.idev.entreumart.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getCarts(token: String) = repository.getCarts(token)

    val counter = MutableLiveData(0)
    val price = MutableLiveData(0)
    val productCount = MutableLiveData(0)
    val product = MutableLiveData<Item>()

    fun getPrice(value: Int) {
        price.postValue(value)
    }

    fun incrementCount(value: Int) {
        counter.postValue(counter.value?.plus(value))
    }

    fun decrementCount(value: Int) {
        counter.postValue(counter.value?.minus(value))
    }

    fun getProductCount(value: Int) {
        productCount.postValue(value)
    }

    fun getProduct(value: Item) {
        product.postValue(value)
    }

    fun deleteCart(token: String, id: Int) = repository.deleteCart(token, id)
}