package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)
