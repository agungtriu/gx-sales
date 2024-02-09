package com.agungtriu.gxsales.ui.dashboard.formlead

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Form(
    val fullName: String,
    val email: String? = null,
    val phone: String,
    val address: String? = null,
    val latitude: String,
    val longitude: String,
    val companyName: String,
    val gender: String? = null,
    val idNumber: String? = null,
    val branchOfficeId: String? = null,
    val imageUrl: Uri? = null
) : Parcelable
