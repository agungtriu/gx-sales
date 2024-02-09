package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class PhoneCodeResponse(

    @field:SerializedName("countries")
    val countries: List<CountriesItem>? = null
)

data class CountriesItem(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)
