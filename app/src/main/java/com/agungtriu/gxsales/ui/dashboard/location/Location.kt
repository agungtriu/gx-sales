package com.agungtriu.gxsales.ui.dashboard.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable
