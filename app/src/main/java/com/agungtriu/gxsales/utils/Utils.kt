package com.agungtriu.gxsales.utils

import android.annotation.SuppressLint
import android.content.Context
import com.agungtriu.gxsales.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object Utils {
    @SuppressLint("SimpleDateFormat")
    fun millisToDisplayDate(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }

    fun displayDate(data: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val originalDate = inputFormat.parse(data)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return outputFormat.format(originalDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToMillis(dateString: String): Long {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date = format.parse(dateString)
        return date?.time ?: 0
    }

    @SuppressLint("SimpleDateFormat")
    fun millisToDate(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun millisToDateTime(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
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