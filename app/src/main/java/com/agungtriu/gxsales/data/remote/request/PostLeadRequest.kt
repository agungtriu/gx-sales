package com.agungtriu.gxsales.data.remote.request

import okhttp3.MultipartBody

data class PostLeadRequest(
    val fullName: MultipartBody.Part,
    val email: MultipartBody.Part? = null,
    val phone: MultipartBody.Part,
    val address: MultipartBody.Part? = null,
    val latitude: MultipartBody.Part,
    val longitude: MultipartBody.Part,
    val companyName: MultipartBody.Part,
    val generalNotes: MultipartBody.Part? = null,
    val gender: MultipartBody.Part? = null,
    val idNumber: MultipartBody.Part? = null,
    val idNumberPhoto: MultipartBody.Part? = null,
    val branchOfficeId: MultipartBody.Part? = null,
    val typeId: MultipartBody.Part,
    val channelId: MultipartBody.Part,
    val mediaId: MultipartBody.Part,
    val sourceId: MultipartBody.Part? = null,
    val statusId: String,
    val probabilityId: MultipartBody.Part
)