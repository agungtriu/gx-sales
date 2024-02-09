package com.agungtriu.gxsales.data

import com.agungtriu.gxsales.data.remote.ApiService
import com.agungtriu.gxsales.data.remote.request.PostLeadRequest
import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.data.remote.response.CountriesItem
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.agungtriu.gxsales.data.remote.response.DataStatus
import com.agungtriu.gxsales.data.remote.response.DeleteLeadResponse
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.data.remote.response.PhoneCodeResponse
import com.agungtriu.gxsales.ui.dashboard.shop.Products
import com.agungtriu.gxsales.utils.Extension.toErrorResponse
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils
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

    override suspend fun postLead(postLeadRequest: PostLeadRequest): Flow<UIState<LeadResponse>> =
        flow {
            emit(UIState.Loading)
            try {
                val result = apiService.postLead(
                    fullName = postLeadRequest.fullName,
                    email = postLeadRequest.email,
                    phone = postLeadRequest.phone,
                    address = postLeadRequest.address,
                    latitude = postLeadRequest.latitude,
                    longitude = postLeadRequest.longitude,
                    companyName = postLeadRequest.companyName,
                    generalNotes = postLeadRequest.generalNotes,
                    gender = postLeadRequest.gender,
                    IDNumber = postLeadRequest.idNumber,
                    IDNumberPhoto = postLeadRequest.idNumberPhoto,
                    branchOfficeId = postLeadRequest.branchOfficeId,
                    probabilityId = postLeadRequest.probabilityId,
                    typeId = postLeadRequest.typeId,
                    channelId = postLeadRequest.channelId,
                    mediaId = postLeadRequest.mediaId,
                    sourceId = postLeadRequest.sourceId,
                )
                if (result.data?.id != null) {
                    emit(UIState.Success(result.data))
                    patchStatus(result.data.id, UpdateStatusRequest(postLeadRequest.statusId))
                } else {
                    throw IllegalArgumentException("Failed to create lead")
                }
            } catch (t: Throwable) {
                emit(UIState.Error(t.toErrorResponse()))
            }
        }

    override suspend fun postUpdateLead(
        id: String,
        postLeadRequest: PostLeadRequest
    ): Flow<UIState<LeadResponse>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.postUpdateLead(
                id = id,
                fullName = postLeadRequest.fullName,
                email = postLeadRequest.email,
                phone = postLeadRequest.phone,
                address = postLeadRequest.address,
                latitude = postLeadRequest.latitude,
                longitude = postLeadRequest.longitude,
                companyName = postLeadRequest.companyName,
                generalNotes = postLeadRequest.generalNotes,
                gender = postLeadRequest.gender,
                IDNumber = postLeadRequest.idNumber,
                IDNumberPhoto = postLeadRequest.idNumberPhoto,
                branchOfficeId = postLeadRequest.branchOfficeId,
                probabilityId = postLeadRequest.probabilityId,
                typeId = postLeadRequest.typeId,
                channelId = postLeadRequest.channelId,
                mediaId = postLeadRequest.mediaId,
                sourceId = postLeadRequest.sourceId,
            )
            if (result.data?.id != null) {
                emit(UIState.Success(result.data))
//                patchStatus(result.data.id, UpdateStatusRequest(postLeadRequest.statusId))
            } else {
                throw IllegalArgumentException("Failed to update lead")
            }
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

    override suspend fun getBranchOffices(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getBranchOffices()
            if (result.data != null) {
                emit(UIState.Success(result.data))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getPhoneCodes(): Flow<UIState<List<CountriesItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = Utils.jsonToObject<PhoneCodeResponse>("country_phone_code.json")
            if (result.countries != null) {
                emit(UIState.Success(result.countries))
            } else {
                throw NullPointerException("data not found")
            }
        } catch (t: Throwable) {
            emit(UIState.Error(t.toErrorResponse()))
        }
    }

    override suspend fun getTypes(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getTypes()
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

    override suspend fun getProbabilities(): Flow<UIState<List<DataItem>>> = flow {
        emit(UIState.Loading)
        try {
            val result = apiService.getProbabilities()
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