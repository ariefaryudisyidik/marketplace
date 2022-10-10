package com.idev.entreumart.ui.market.product.search

import androidx.lifecycle.*
import com.idev.entreumart.data.remote.response.model.Item
import com.idev.entreumart.data.repository.MarketRepository
import com.idev.entreumart.utils.Resource
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