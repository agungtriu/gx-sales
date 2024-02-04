package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateStatusResponse(

    @field:SerializedName("data")
    val data: DataStatus? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataStatus(

    @field:SerializedName("number")
    val number: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status")
    val status: Status? = null
)

data class Status(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
