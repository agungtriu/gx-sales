package com.agungtriu.gxsales.utils

object Config {
    const val DATASTORE_NAME = "gx-sales"
    const val API_BASE_URL = "https://phplaravel-918600-4275378.cloudwaysapps.com/api/"
    val PRELOGIN_ENDPOINT = listOf(
        "${API_BASE_URL}login"
    )
}
