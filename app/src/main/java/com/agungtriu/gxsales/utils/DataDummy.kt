package com.agungtriu.gxsales.utils

import com.agungtriu.gxsales.ui.dashboard.shop.Products

object DataDummy {
    val dummyProducts = Utils.jsonToObject<Products>("products.json")
}