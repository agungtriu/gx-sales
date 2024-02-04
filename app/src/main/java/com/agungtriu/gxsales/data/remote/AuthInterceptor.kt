package com.agungtriu.gxsales.data.remote

import com.agungtriu.gxsales.data.datastore.DataStoreManager
import com.agungtriu.gxsales.utils.Config.PRELOGIN_ENDPOINT
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestUrl = originalRequest.url.toString()
        val isPreLoginRequest = PRELOGIN_ENDPOINT.contains(requestUrl)
        val requestWithAuth =
            if (!isPreLoginRequest) {
                val token = runBlocking {
                    dataStoreManager.getStatus().first()
                }
                originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer ${token.token}")
            } else {
                originalRequest.newBuilder()
            }

        return chain.proceed(requestWithAuth.build())
    }
}