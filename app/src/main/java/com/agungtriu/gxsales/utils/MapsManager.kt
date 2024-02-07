package com.agungtriu.gxsales.utils

import android.content.Context
import android.location.Geocoder
import java.io.IOException

object MapsManager {
    fun getLocationFromAddress(context: Context, strAddress: String): Pair<Double, Double>? {
        val geocoder = Geocoder(context)
        val address: List<android.location.Address>?
        var p1: android.location.Address? = null

        try {
            // Getting a maximum of 1 Address that matches the input text
            address = geocoder.getFromLocationName(strAddress, 1)

            if (!address.isNullOrEmpty()) {
                p1 = address[0]
                return Pair(p1.latitude, p1.longitude)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    // Reverse Geocoding (Latitude and Longitude to Address)
    fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context)
        val address: List<android.location.Address>?
        var addressText: String? = null

        try {
            // Getting a maximum of 1 Address from latitude and longitude
            address = geocoder.getFromLocation(latitude, longitude, 1)

            if (!address.isNullOrEmpty()) {
                val returnedAddress = address[0]
                val stringBuilder = StringBuilder()

                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    stringBuilder.append(returnedAddress.getAddressLine(i)).append("\n")
                }

                addressText = stringBuilder.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressText
    }
}