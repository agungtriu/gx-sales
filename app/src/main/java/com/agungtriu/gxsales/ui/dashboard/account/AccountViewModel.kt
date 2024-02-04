package com.agungtriu.gxsales.ui.dashboard.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.AuthorizationRepository
import com.agungtriu.gxsales.data.remote.response.LogoutResponse
import com.agungtriu.gxsales.data.remote.response.Profile
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val authorizationRepository: AuthorizationRepository) :
    ViewModel() {
    private var _resultProfile = MutableLiveData<UIState<Profile>>()
    val resultProfile: LiveData<UIState<Profile>> get() = _resultProfile

    private var _resultLogout = MutableLiveData<UIState<LogoutResponse>>()
    val resultLogout: LiveData<UIState<LogoutResponse>> get() = _resultLogout

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            authorizationRepository.getProfile().collect {
                _resultProfile.value = it
            }
        }
    }


    fun postLogout() {
        viewModelScope.launch {
            authorizationRepository.postLogout().collect {
                _resultLogout.value = it
            }
        }
    }
}