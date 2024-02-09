package com.agungtriu.gxsales.ui.dashboard.leads.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val fromDate: Long? = null,
    val toDate: Long? = null,
    val status: String? = null,
    val channel: String? = null,
    val media: String? = null,
    val source: String? = null
) : Parcelable
