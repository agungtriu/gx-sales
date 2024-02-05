package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.remote.ApiService
import com.agungtriu.gxsales.data.remote.response.Dashboard
import com.agungtriu.gxsales.utils.Extension.toErrorResponse
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DashboardRepositoryImp @Inject constructor(private val apiService: ApiService) :
    DashboardRepository {
    override suspend fun getDashboard(
        fromDate: String?,
        toDate: String?
    ): Flow<UIState<Dashboard>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getDashboard(fromDate, toDate).data
            if (result != null) {
                emit(UIState.Success(result))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }
}