package com.excode.marketplace.ui.market.product.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.excode.marketplace.data.local.datastore.UserPreference
import com.excode.marketplace.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: MarketRepository,
    pref: UserPreference
) : ViewModel() {

    val token = pref.getToken.asLiveData()

    fun getCarts(token: String) = repository.getCarts(token)

    val counter: MutableLiveData<Int> = MutableLiveData(0)
    val price: MutableLiveData<Int> = MutableLiveData(0)

    fun getPrice(value: Int) {
        price.postValue(value)
    }

    fun incrementCount() {
        counter.postValue(counter.value?.plus(1))
    }

    fun decrementCount() {
        counter.postValue(counter.value?.minus(1))
    }
}