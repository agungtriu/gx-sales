package com.agungtriu.gxsales.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @field:SerializedName("products")
    val products: List<Product>? = null
)

data class Product(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("price")
    val price: Long,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("tax")
    val tax: Int,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("stock")
    val stock: Int
)
