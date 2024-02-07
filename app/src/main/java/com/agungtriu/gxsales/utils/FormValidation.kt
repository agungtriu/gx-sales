package com.agungtriu.gxsales.utils

import android.content.Context
import android.net.Uri
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object FormValidation {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private const val maxLen = 8
    fun isPasswordValid(text: String): Boolean {
        return text.length >= maxLen
    }

    fun textInputToString(
        editText: TextInputEditText,
        title: TextView,
        nullable: Boolean,
        context: Context,
        flag: String? = null
    ): String? {
        return if (!editText.text.isNullOrBlank()) {
            return when (flag) {
                "email" -> {
                    return if (isEmailValid(editText.text.toString())) {
                        editText.text.toString()
                    } else {
                        editText.error = context.getString(R.string.all_invalid_email)
                        null
                    }
                }

                "phone" -> {
                    "0".plus(editText.text.toString())
                }

                else -> {
                    editText.text.toString()
                }
            }
        } else {
            if (!nullable) {
                editText.error = title.text.toString().substring(0, title.text.length - 1)
                    .plus(" cannot be null")
            }
            null
        }
    }

    fun dropDownInputToString(
        editText: AutoCompleteTextView,
        list: List<DataItem>?,
        title: TextView,
        nullable: Boolean
    ): String? {
        return if (!editText.text.isNullOrBlank() && list != null) {
            list.filter { it.name !=null && it.name == editText.text.toString() }[0].id.toString()
        } else {
            if (!nullable) {
                editText.error = title.text.toString().substring(0, title.text.length - 1)
                    .plus(" cannot be null!")
            }
            null
        }
    }

    fun textInputToMultipart(
        editText: TextInputEditText,
        title: TextView,
        key: String,
        nullable: Boolean
    ): MultipartBody.Part? {
        return if (!editText.text.isNullOrBlank()) {
            val textBody = editText.text.toString().toRequestBody("text/plain".toMediaType())
            MultipartBody.Part.createFormData(key, null, textBody)
        } else {
            if (!nullable) {
                editText.error = title.text.toString().substring(0, title.text.length - 1)
                    .plus(" cannot be null!")
            }
            null
        }
    }

    fun dropDownInputToMultipart(
        editText: AutoCompleteTextView,
        title: TextView,
        list: List<DataItem>?,
        key: String,
        nullable: Boolean
    ): MultipartBody.Part? {
        return if (!editText.text.isNullOrBlank() && list != null) {
            val textBody =
                list.filter { it.name !=null && it.name == editText.text.toString() }[0].id.toString()
                    .toRequestBody("text/plain".toMediaType())
            MultipartBody.Part.createFormData(key, null, textBody)
        } else {
            if (!nullable) {
                editText.error = title.text.toString().substring(0, title.text.length - 1)
                    .plus(" cannot be null!")
            }
            null
        }
    }

    fun imageToMultiPart(
        imageUri: Uri?,
        key: String,
        context: Context
    ): MultipartBody.Part? {
        val file = imageUri?.let { uri -> PhotoUriManager.uriToFile(context, uri) }
        val fileBody = file?.asRequestBody("image/*".toMediaType())
        return fileBody?.let { requestBody ->
            MultipartBody.Part.createFormData(
                key,
                file.name,
                requestBody
            )
        }
    }

    fun stringToMultipart(
        text: String?,
        key: String
    ): MultipartBody.Part? {
        return if (!text.isNullOrBlank()) {
            val textBody = text.toRequestBody("text/plain".toMediaType())
            MultipartBody.Part.createFormData(key, null, textBody)
        } else {
            null
        }
    }
}
