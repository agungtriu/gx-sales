package com.agungtriu.gxsales.utils

import com.agungtriu.gxsales.data.remote.response.ErrorResponse

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val error: ErrorResponse) : UIState<Nothing>()
}