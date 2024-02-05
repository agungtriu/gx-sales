package com.agungtriu.gxsales.data.remote

import com.agungtriu.gxsales.data.datastore.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            return runBlocking {
                dataStoreManager.logout()
                return@runBlocking null
            }
        }
    }
}
