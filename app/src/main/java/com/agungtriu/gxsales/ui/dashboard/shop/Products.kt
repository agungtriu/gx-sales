package com.agungtriu.gxsales.ui.dashboard.shop

import com.google.gson.annotations.SerializedName

data class Products(

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
