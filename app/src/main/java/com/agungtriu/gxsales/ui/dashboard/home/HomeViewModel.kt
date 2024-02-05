package com.agungtriu.gxsales.ui.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.AuthorizationRepository
import com.agungtriu.gxsales.data.DashboardRepository
import com.agungtriu.gxsales.data.remote.response.Dashboard
import com.agungtriu.gxsales.data.remote.response.Profile
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils.currentMillis
import com.agungtriu.gxsales.utils.Utils.millisToDate
import com.agungtriu.gxsales.utils.Utils.millisToDisplayDate
import com.agungtriu.gxsales.utils.Utils.nextDayMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val dashboardRepository: DashboardRepository
) : ViewModel() {
    private var _resultProfile = MutableLiveData<UIState<Profile>>()
    val resultProfile: LiveData<UIState<Profile>> get() = _resultProfile

    private var _resultDashboard = MutableLiveData<UIState<Dashboard>>()
    val resultDashboard: LiveData<UIState<Dashboard>> get() = _resultDashboard

    init {
        getProfile()
        getDashboard()
    }

    private fun getProfile() {
        viewModelScope.launch {
            authorizationRepository.getProfile().collect {
                _resultProfile.value = it
            }
        }
    }

    fun getDashboard(
        fromDate: String? = millisToDate(currentMillis()),
        toDate: String? = millisToDate(nextDayMillis(7))
    ) {
        viewModelScope.launch {
            dashboardRepository.getDashboard(fromDate, toDate).collect {
                _resultDashboard.value = it
            }
        }
    }
}