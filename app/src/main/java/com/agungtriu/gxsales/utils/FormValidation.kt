package com.agungtriu.gxsales.utils

object FormValidation {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private const val maxLen = 8
    fun isPasswordValid(text: String): Boolean {
        return text.length >= maxLen
    }
}
