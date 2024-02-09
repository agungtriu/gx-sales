package com.agungtriu.gxsales.ui.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.AuthorizationRepository
import com.agungtriu.gxsales.data.DashboardRepository
import com.agungtriu.gxsales.data.remote.response.Dashboard
import com.agungtriu.gxsales.data.remote.response.Profile
import com.agungtriu.gxsales.utils.Date.beforeDayMillis
import com.agungtriu.gxsales.utils.Date.endOfDayMillis
import com.agungtriu.gxsales.utils.Date.millisToDate
import com.agungtriu.gxsales.utils.UIState
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
    var fromMillis = beforeDayMillis(7)
    var toMillis = endOfDayMillis()

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
        fromDate: String? = millisToDate(fromMillis),
        toDate: String? = millisToDate(toMillis)
    ) {
        viewModelScope.launch {
            dashboardRepository.getDashboard(fromDate, toDate).collect {
                _resultDashboard.value = it
            }
        }
    }
}
