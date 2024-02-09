package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.datastore.model.StatusModel
import com.agungtriu.gxsales.data.remote.request.LoginRequest
import com.agungtriu.gxsales.data.remote.response.LoginResponse
import com.agungtriu.gxsales.data.remote.response.LogoutResponse
import com.agungtriu.gxsales.data.remote.response.Profile
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {
    suspend fun postLogin(loginRequest: LoginRequest): Flow<UIState<LoginResponse>>
    suspend fun postLogout(): Flow<UIState<LogoutResponse>>
    suspend fun getProfile(): Flow<UIState<Profile>>

    fun getStatus(): Flow<StatusModel>
    suspend fun login(tokenModel: StatusModel)
    suspend fun logout()
}
