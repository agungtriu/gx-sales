package com.agungtriu.gxsales.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object Date {
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

    fun endOfDayMillis(): Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private fun startOfDayMillis(): Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    fun beforeDayMillis(day: Long = 1): Long = startOfDayMillis().minus(TimeUnit.DAYS.toMillis(day))
}
