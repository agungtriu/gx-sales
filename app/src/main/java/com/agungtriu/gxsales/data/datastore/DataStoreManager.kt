package com.agungtriu.gxsales.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.agungtriu.gxsales.data.datastore.model.StatusModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun login(tokenModel: StatusModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = tokenModel.token ?: ""
            preferences[TOKEN_TYPE_KEY] = tokenModel.type ?: ""
        }
    }

    fun getStatus(): Flow<StatusModel> {
        return dataStore.data.map { preferences ->
            StatusModel(
                token = preferences[TOKEN_KEY] ?: "",
                type = preferences[TOKEN_TYPE_KEY] ?: "",
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
            preferences[TOKEN_TYPE_KEY] = ""
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val TOKEN_TYPE_KEY = stringPreferencesKey("token_type")
    }
}
