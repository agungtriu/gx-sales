package com.agungtriu.gxsales.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.agungtriu.gxsales.R
import com.facebook.shimmer.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

object Utils {
    @SuppressLint("SimpleDateFormat")
    fun millisToDisplayDate(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }
    @SuppressLint("SimpleDateFormat")
    fun millisToDate(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }

    fun currentMillis(): Long = System.currentTimeMillis()
    fun nextDayMillis(day: Long = 1): Long = currentMillis().plus(TimeUnit.DAYS.toMillis(day))

    fun copyright(context: Context) = context.getString(R.string.all_copyright)
        .plus(" ")
        .plus(millisToDate(currentMillis()).split("/").last())
        .plus(" - ")
        .plus("v1.0.0")
}