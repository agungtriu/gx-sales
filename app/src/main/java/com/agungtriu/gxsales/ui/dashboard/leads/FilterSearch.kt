package com.agungtriu.gxsales.ui.dashboard.leads

import com.agungtriu.gxsales.ui.dashboard.leads.filter.Filter

data class FilterSearch(
    val search: String? = null,
    val fromDate: Long? = null,
    val toDate: Long? = null,
    val status: String? = null,
    val channel: String? = null,
    val media: String? = null,
    val source: String? = null
) {
    fun toFilter() = Filter(
        fromDate = this.fromDate,
        toDate = this.toDate,
        status = this.status,
        channel = this.channel,
        media = this.media,
        source = this.source
    )
}
