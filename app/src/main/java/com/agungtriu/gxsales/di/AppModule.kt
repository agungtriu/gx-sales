package com.agungtriu.gxsales.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.agungtriu.gxsales.data.datastore.DataStoreManager
import com.agungtriu.gxsales.data.remote.ApiService
import com.agungtriu.gxsales.data.remote.AuthAuthenticator
import com.agungtriu.gxsales.data.remote.AuthInterceptor
import com.agungtriu.gxsales.utils.Config.API_BASE_URL
import com.agungtriu.gxsales.utils.Config.DATASTORE_NAME
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAuthInterceptor(dataStoreManager: DataStoreManager): AuthInterceptor =
        AuthInterceptor(dataStoreManager)

    @Singleton
    @Provides
    fun provideAuthAuthentication(dataStoreManager: DataStoreManager): Authenticator =
        AuthAuthenticator(dataStoreManager)

    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext appContext: Context): ChuckerCollector {
        return ChuckerCollector(
            context = appContext,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext appContext: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor
            .Builder(appContext)
            .collector(chuckerCollector)
            .createShortcut(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideClient(
        chuckerInterceptor: ChuckerInterceptor,
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(chuckerInterceptor)
        .authenticator(authAuthenticator)
        .build()

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .client(client)
            .build()
            .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideDatastore(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(
            PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile(DATASTORE_NAME)
                }
            )
        )
}
