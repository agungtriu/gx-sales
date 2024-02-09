package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.ui.dashboard.shop.Product
import com.agungtriu.gxsales.ui.dashboard.shop.Products
import com.agungtriu.gxsales.utils.Extension.toErrorResponse
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopRepositoryImp @Inject constructor() : ShopRepository {
    override suspend fun getProducts(): Flow<UIState<List<Product>>> = flow {
        emit(UIState.Loading)
        try {
            val result = Utils.jsonToObject<Products>("products.json")
            if (result.products != null) {
                emit(UIState.Success(result.products))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }
}