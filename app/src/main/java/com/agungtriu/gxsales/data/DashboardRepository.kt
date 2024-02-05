package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.remote.ApiService
import com.agungtriu.gxsales.data.remote.response.Dashboard
import com.agungtriu.gxsales.data.remote.response.DashboardResponse
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DashboardRepository{
    suspend fun getDashboard(fromDate: String?, toDate: String?): Flow<UIState<Dashboard>>
}