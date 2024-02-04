package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

    @field:SerializedName("data")
    val data: Dashboard? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class Dashboard(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("statuses")
    val statuses: List<StatusesItem>? = null
)

data class StatusesItem(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)
