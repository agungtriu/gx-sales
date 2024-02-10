package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.datastore.DataStoreManager
import com.agungtriu.gxsales.data.datastore.model.StatusModel
import com.agungtriu.gxsales.data.remote.ApiService
import com.agungtriu.gxsales.data.remote.request.LoginRequest
import com.agungtriu.gxsales.data.remote.response.LoginResponse
import com.agungtriu.gxsales.data.remote.response.LogoutResponse
import com.agungtriu.gxsales.data.remote.response.Profile
import com.agungtriu.gxsales.utils.Extension.toErrorResponse
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorizationRepositoryImp @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val apiService: ApiService
) : AuthorizationRepository {
    override suspend fun postLogin(loginRequest: LoginRequest): Flow<UIState<LoginResponse>> =
        flow {
            emit(UIState.Loading)
            try {
                val result = apiService.postLogin(loginRequest)
                if (result.status == "success") {
                    login(tokenModel = StatusModel(token = result.token, type = result.type))
                    emit(UIState.Success(result))
                } else {
                     throw IllegalStateException("error")
                }
            } catch (t: Throwable) {
                emit(UIState.Error(t.toErrorResponse()))
            }
        }

    override suspend fun login(tokenModel: StatusModel) {
        dataStoreManager.login(tokenModel)
    }

    override suspend fun postLogout(): Flow<UIState<LogoutResponse>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.postLogout()
            if (result.status == "success") {
                logout()
                emit(UIState.Success(result))
            } else {
                throw IllegalStateException("error")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getProfile(): Flow<UIState<Profile>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getProfile()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun logout() {
        dataStoreManager.logout()
    }

    override fun getStatus(): Flow<StatusModel> = dataStoreManager.getStatus()
}
