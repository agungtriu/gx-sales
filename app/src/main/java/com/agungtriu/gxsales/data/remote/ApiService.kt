package com.agungtriu.gxsales.data.remote

import com.agungtriu.gxsales.data.remote.request.LoginRequest
import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.data.remote.response.BranchOfficeResponse
import com.agungtriu.gxsales.data.remote.response.ChannelResponse
import com.agungtriu.gxsales.data.remote.response.CreateLeadResponse
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
import com.agungtriu.gxsales.data.remote.response.UpdateLeadResponse
import com.agungtriu.gxsales.data.remote.response.UpdateStatusResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("settings/types")
    suspend fun getTypes(): TypeResponse

    @GET("settings/probabilities")
    suspend fun getProbabilities(): ProbabilityResponse

    @GET("settings/statuses")
    suspend fun getStatuses(): StatusResponse

    @GET("settings/channels")
    suspend fun getChannels(): ChannelResponse

    @GET("settings/medias")
    suspend fun getMedias(): MediaResponse

    @GET("settings/sources")
    suspend fun getSources(): SourceResponse

    @GET("leads")
    suspend fun getLeads(): LeadsResponse

    @Multipart
    @POST("leads")
    suspend fun postLead(
        @Part fullName: MultipartBody.Part,
        @Part email: MultipartBody.Part?,
        @Part phone: MultipartBody.Part,
        @Part address: MultipartBody.Part?,
        @Part latitude: MultipartBody.Part,
        @Part longitude: MultipartBody.Part,
        @Part companyName: MultipartBody.Part,
        @Part generalNotes: MultipartBody.Part?,
        @Part gender: MultipartBody.Part?,
        @Part IDNumber: MultipartBody.Part?,
        @Part IDNumberPhoto: MultipartBody.Part?,
        @Part branchOfficeId: MultipartBody.Part?,
        @Part probabilityId: MultipartBody.Part?,
        @Part typeId: MultipartBody.Part,
        @Part channelId: MultipartBody.Part,
        @Part mediaId: MultipartBody.Part,
        @Part sourceId: MultipartBody.Part?,
    ): CreateLeadResponse

    @PATCH("leads/{id}/status")
    suspend fun patchStatus(
        @Path("id") id: Int,
        @Body updateStatusRequest: UpdateStatusRequest
    ): UpdateStatusResponse

    @Multipart
    @POST("leads/{id}/update")
    suspend fun postUpdateLead(
        @Path("id") id: String,
        @Part fullName: MultipartBody.Part,
        @Part email: MultipartBody.Part?,
        @Part phone: MultipartBody.Part,
        @Part address: MultipartBody.Part?,
        @Part latitude: MultipartBody.Part,
        @Part longitude: MultipartBody.Part,
        @Part companyName: MultipartBody.Part,
        @Part generalNotes: MultipartBody.Part?,
        @Part gender: MultipartBody.Part?,
        @Part IDNumber: MultipartBody.Part?,
        @Part IDNumberPhoto: MultipartBody.Part?,
        @Part branchOfficeId: MultipartBody.Part?,
        @Part probabilityId: MultipartBody.Part?,
        @Part typeId: MultipartBody.Part,
        @Part channelId: MultipartBody.Part,
        @Part mediaId: MultipartBody.Part,
        @Part sourceId: MultipartBody.Part?,
    ): UpdateLeadResponse

    @GET("leads/{id}")
    suspend fun getLead(@Path("id") id: Int): LeadResponse

    @DELETE("leads/{id}")
    suspend fun deleteLead(@Path("id") id: Int): DeleteLeadResponse

    @GET("leads/dashboard")
    suspend fun getDashboard(
        @Query("fromDate") fromDate: String?,
        @Query("toDate") toDate: String?
    ): DashboardResponse
}