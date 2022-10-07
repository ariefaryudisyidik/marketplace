package com.excode.marketplace.ui.market.product.search

import androidx.lifecycle.*
import com.excode.marketplace.data.remote.response.model.Item
import com.excode.marketplace.data.repository.MarketRepository
import com.excode.marketplace.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val marketRepository: MarketRepository,
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Item>>>()
    val products: LiveData<Resource<List<Item>>> = _products

    fun searchProduct(productName: String) {
        viewModelScope.launch {
            marketRepository.searchProduct(productName).asFlow().collect {
                _products.postValue(it)
            }
        }
    }
}