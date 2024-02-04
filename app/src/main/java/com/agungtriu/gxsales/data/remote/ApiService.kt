package com.agungtriu.gxsales.data.remote

import com.agungtriu.gxsales.data.remote.request.LoginRequest
import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.data.remote.response.BranchOfficeResponse
import com.agungtriu.gxsales.data.remote.response.ChannelResponse
import com.agungtriu.gxsales.data.remote.response.DashboardResponse
import com.agungtriu.gxsales.data.remote.response.DeleteLeadResponse
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.data.remote.response.LeadsResponse
import com.agungtriu.gxsales.data.remote.response.LoginResponse
import com.agungtriu.gxsales.data.remote.response.LogoutResponse
import com.agungtriu.gxsales.data.remote.response.MediaResponse
import com.agungtriu.gxsales.data.remote.response.ProbabilityResponse
import com.agungtriu.gxsales.data.remote.response.ProfileResponse
import com.agungtriu.gxsales.data.remote.response.SourceResponse
import com.agungtriu.gxsales.data.remote.response.StatusResponse
import com.agungtriu.gxsales.data.remote.response.TypeResponse
import com.agungtriu.gxsales.data.remote.response.UpdateStatusResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("logout")
    suspend fun postLogout(): LogoutResponse

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @GET("settings/branch-offices")
    suspend fun getBranchOffices(): BranchOfficeResponse

    @GET("settings/type")
    suspend fun getTypes(): TypeResponse

    @GET("settings/probability")
    suspend fun getProbabilities(): ProbabilityResponse

    @GET("settings/status")
    suspend fun getStatuses(): StatusResponse

    @GET("settings/channel")
    suspend fun getChannels(): ChannelResponse

    @GET("settings/media")
    suspend fun getMedias(): MediaResponse

    @GET("settings/source")
    suspend fun getSources(): SourceResponse

    @GET("leads")
    suspend fun getLeads(): LeadsResponse

    @GET("leads/{id}")
    suspend fun getLead(@Path("id") id: String): LeadResponse

    @DELETE("leads/{id}")
    suspend fun deleteLead(@Path("id") id: String): DeleteLeadResponse

    @PATCH("leads/{id}/status")
    suspend fun patchStatus(
        @Path("id") id: String,
        @Body updateStatusRequest: UpdateStatusRequest
    ): UpdateStatusResponse

    @GET("leads/dashboard")
    suspend fun getDashboard(
        @Query("fromDate") fromDate: String?,
        @Query("toDate") toDate: String?
    ): DashboardResponse
}