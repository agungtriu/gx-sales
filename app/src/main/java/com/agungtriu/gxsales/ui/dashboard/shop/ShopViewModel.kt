package com.agungtriu.gxsales.ui.dashboard.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agungtriu.gxsales.utils.DataDummy

class ShopViewModel : ViewModel() {
    private var _resultProducts = MutableLiveData<Products>()
    val resultProducts: LiveData<Products> get() = _resultProducts

    init {
        getProducts()
    }

    private fun getProducts() {
        _resultProducts.value = DataDummy.dummyProducts
    }
}