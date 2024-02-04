package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateLeadResponse(

    @field:SerializedName("data")
    val data: LeadResponse? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)