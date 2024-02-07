package com.agungtriu.gxsales.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeadResponse(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("probability")
    val probability: DataItem? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("companyName")
    val companyName: String? = null,

    @field:SerializedName("channel")
    val channel: DataItem? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("media")
    val media: DataItem? = null,

    @field:SerializedName("source")
    val source: DataItem? = null,

    @field:SerializedName("type")
    val type: DataItem? = null,

    @field:SerializedName("number")
    val number: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("branchOffice")
    val branchOffice: DataItem? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("IDNumberPhoto")
    val iDNumberPhoto: String? = null,

    @field:SerializedName("generalNotes")
    val generalNotes: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("IDNumber")
    val iDNumber: String? = null,

    @field:SerializedName("status")
    val status: DataItem? = null
) : Parcelable