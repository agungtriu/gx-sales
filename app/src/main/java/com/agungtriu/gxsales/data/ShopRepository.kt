package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.remote.response.Product
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    suspend fun getProducts(): Flow<UIState<List<Product>>>
}
