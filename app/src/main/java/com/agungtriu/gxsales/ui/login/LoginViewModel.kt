package com.agungtriu.gxsales.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.AuthorizationRepository
import com.agungtriu.gxsales.data.remote.request.LoginRequest
import com.agungtriu.gxsales.data.remote.response.LoginResponse
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authorizationRepository: AuthorizationRepository) :
    ViewModel() {
    private var _resultPostLogin = MutableLiveData<UIState<LoginResponse>>()
    val resultPostLogin: LiveData<UIState<LoginResponse>> get() = _resultPostLogin

    fun postLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            authorizationRepository.postLogin(loginRequest).collect {
                _resultPostLogin.value = it
            }
        }
    }
}