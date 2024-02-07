package com.agungtriu.gxsales.utils

import com.agungtriu.gxsales.data.remote.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.text.NumberFormat
import java.util.Locale

object Extension {
    fun Throwable.toErrorResponse(): ErrorResponse {
        return when (this) {
            is HttpException -> {
                Gson().fromJson(
                    this.response()?.errorBody()?.string(), ErrorResponse::class.java
                ) ?: ErrorResponse()
            }

            is IOException -> {
                ErrorResponse(
                    status = HttpURLConnection.HTTP_GATEWAY_TIMEOUT.toString(),
                    message = this.message.toString()
                )
            }

            else -> {
                ErrorResponse(status = null, message = this.message)
            }
        }
    }

    fun Long.toDisplay(): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(this)
    }
}