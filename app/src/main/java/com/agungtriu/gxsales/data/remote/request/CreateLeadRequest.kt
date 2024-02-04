package com.agungtriu.gxsales.data.remote.request

data class CreateLeadRequest(
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val companyName: String,
    val generalNotes: String,
    val gender: String,
    val IDNumber: String,
    val branchOfficeId: String,
    val probabilityId: String,
    val typeId: String,
    val channelId: String,
    val mediaId: String,
    val sourceId: String
)