package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.agungtriu.gxsales.data.remote.response.DataStatus
import com.agungtriu.gxsales.data.remote.response.DeleteLeadResponse
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow


interface LeadsRepository {
    suspend fun getLeads(): Flow<UIState<List<LeadResponse>>>
    suspend fun getLead(id: Int): Flow<UIState<LeadResponse>>
    suspend fun delete(id: Int): Flow<UIState<DeleteLeadResponse>>
    suspend fun patchStatus(
        id: Int,
        updateStatusRequest: UpdateStatusRequest
    ): Flow<UIState<DataStatus>>

    suspend fun getStatuses(): Flow<UIState<List<DataItem>>>
    suspend fun getChannels(): Flow<UIState<List<DataItem>>>
    suspend fun getMedias(): Flow<UIState<List<DataItem>>>
    suspend fun getSources(): Flow<UIState<List<DataItem>>>
}