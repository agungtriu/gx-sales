package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class SourceResponse(

    @field:SerializedName("data")
    val data: List<DataItem>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
