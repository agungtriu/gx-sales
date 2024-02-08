package com.agungtriu.gxsales.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.agungtriu.gxsales.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Calendar
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
    fun nextDayMillis(day: Long = 1): Long = endOfDayMillis().plus(TimeUnit.DAYS.toMillis(day))

    fun copyright(context: Context) = context.getString(R.string.all_copyright)
        .plus(" ")
        .plus(millisToDate(endOfDayMillis()).split("/").last())
        .plus(" - ")
        .plus("v1.0.0")

    fun startShimmerInput(shimmer: ShimmerFrameLayout, layout: TextInputLayout) {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        layout.visibility = View.INVISIBLE
    }

    fun stopShimmerInput(shimmer: ShimmerFrameLayout, layout: TextInputLayout) {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        layout.visibility = View.VISIBLE

    }

    fun closeSoftKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun readTestResourceFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val inputStreamReader = InputStreamReader(inputStream)
        return inputStreamReader.readText()
    }

    inline fun <reified T> jsonToObject(fileName: String): T {
        val response = readTestResourceFile(fileName)
        return Gson().fromJson(response, T::class.java)
    }
}