package com.agungtriu.gxsales.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.agungtriu.gxsales.data.AuthorizationRepository
import com.agungtriu.gxsales.data.datastore.model.StatusModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val authorizationRepository: AuthorizationRepository) :
    ViewModel() {
    fun getStatus(): Boolean {
        return runBlocking {
            !authorizationRepository.getStatus().first().token.isNullOrBlank()
        }
    }

    fun getAuthorization(): LiveData<StatusModel> {
        return authorizationRepository.getStatus().asLiveData()
    }
}
