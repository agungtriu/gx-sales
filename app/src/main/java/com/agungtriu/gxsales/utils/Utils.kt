package com.agungtriu.gxsales.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.utils.Date.endOfDayMillis
import com.agungtriu.gxsales.utils.Date.millisToDate
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import java.io.InputStreamReader

object Utils {

    fun copyright(context: Context) = context.getString(R.string.all_copyright)
        .plus(" ")
        .plus(millisToDate(endOfDayMillis()).split("/").last())
        .plus(" - ")
        .plus("v1.0.0")

    fun startShimmer(shimmer: ShimmerFrameLayout, layout: View) {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        layout.visibility = View.INVISIBLE
    }

    fun stopShimmer(shimmer: ShimmerFrameLayout, layout: View) {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        layout.visibility = View.VISIBLE
    }

    fun closeSoftKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun readResourceFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val inputStreamReader = InputStreamReader(inputStream)
        return inputStreamReader.readText()
    }

    inline fun <reified T> jsonToObject(fileName: String): T {
        val response = readResourceFile(fileName)
        return Gson().fromJson(response, T::class.java)
    }
}
