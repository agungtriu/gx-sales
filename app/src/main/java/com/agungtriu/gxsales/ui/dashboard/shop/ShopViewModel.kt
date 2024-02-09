package com.agungtriu.gxsales.ui.dashboard.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.ShopRepository
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val shopRepository: ShopRepository) : ViewModel() {
    private var _resultProducts = MutableLiveData<UIState<List<Product>>>()
    val resultProducts: LiveData<UIState<List<Product>>> get() = _resultProducts
    var list: List<Product>? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            shopRepository.getProducts().collect {
                _resultProducts.value = it
            }

        }
    }
}