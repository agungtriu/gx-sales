package com.agungtriu.gxsales.ui.dashboard.location

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agungtriu.gxsales.utils.MapsManager

class LocationViewModel : ViewModel() {
    private var _savedLocation = MutableLiveData<Location>()
    val savedLocation: LiveData<Location> get() = _savedLocation

    fun setLocation(context: Context, latitude: Double, longitude: Double) {
        _savedLocation.value = Location(
            MapsManager.getAddressFromLocation(context, latitude, longitude),
            latitude,
            longitude
        )
    }

    fun resetLocation() {
        _savedLocation.value = Location()
    }
}