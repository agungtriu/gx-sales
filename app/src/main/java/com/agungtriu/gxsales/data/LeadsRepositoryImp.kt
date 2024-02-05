package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.remote.ApiService
import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.agungtriu.gxsales.data.remote.response.DataStatus
import com.agungtriu.gxsales.data.remote.response.DeleteLeadResponse
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.utils.Extension.toErrorResponse
import com.agungtriu.gxsales.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LeadsRepositoryImp @Inject constructor(private val apiService: ApiService) : LeadsRepository {
    override suspend fun getLeads(): Flow<UIState<List<LeadResponse>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getLeads()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getLead(id: Int): Flow<UIState<LeadResponse>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getLead(id)
            emit(UIState.Success(result))
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun delete(id: Int): Flow<UIState<DeleteLeadResponse>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.deleteLead(id)
            emit(UIState.Success(result))
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun patchStatus(
        id: Int,
        updateStatusRequest: UpdateStatusRequest
    ): Flow<UIState<DataStatus>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.patchStatus(id, updateStatusRequest)
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getStatuses(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getStatuses()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getChannels(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getChannels()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getMedias(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getMedias()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getSources(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getSources()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

}