package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class LeadsResponse(

    @field:SerializedName("pagination")
    val pagination: Pagination? = null,

    @field:SerializedName("data")
    val data: List<LeadResponse>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class Pagination(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("perPage")
    val perPage: Int? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("totalPages")
    val totalPages: Int? = null,

    @field:SerializedName("currentPage")
    val currentPage: Int? = null
)
