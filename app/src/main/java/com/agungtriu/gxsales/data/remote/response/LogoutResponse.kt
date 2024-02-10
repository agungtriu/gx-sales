package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
