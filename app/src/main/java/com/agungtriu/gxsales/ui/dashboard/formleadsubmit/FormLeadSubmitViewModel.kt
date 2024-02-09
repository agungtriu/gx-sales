package com.agungtriu.gxsales.ui.dashboard.formleadsubmit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.LeadsRepository
import com.agungtriu.gxsales.data.remote.request.PostLeadRequest
import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.agungtriu.gxsales.data.remote.response.DataStatus
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.ui.dashboard.formlead.Form
import com.agungtriu.gxsales.ui.dashboard.formlead.FormLeadFragment
import com.agungtriu.gxsales.ui.dashboard.formlead.FormLeadFragment.Companion.FORM_KEY
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormLeadSubmitViewModel @Inject constructor(
    private val leadsRepository: LeadsRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private var _resultTypes = MutableLiveData<UIState<List<DataItem>>>()
    val resultTypes: LiveData<UIState<List<DataItem>>> get() = _resultTypes
    var types: List<DataItem>? = null
    private var _resultChannels = MutableLiveData<UIState<List<DataItem>>>()
    val resultChannels: LiveData<UIState<List<DataItem>>> get() = _resultChannels
    var channels: List<DataItem>? = null
    private var _resultMedias = MutableLiveData<UIState<List<DataItem>>>()
    val resultMedias: LiveData<UIState<List<DataItem>>> get() = _resultMedias
    var medias: List<DataItem>? = null
    private var _resultSources = MutableLiveData<UIState<List<DataItem>>>()
    val resultSources: LiveData<UIState<List<DataItem>>> get() = _resultSources
    var sources: List<DataItem>? = null
    private var _resultStatuses = MutableLiveData<UIState<List<DataItem>>>()
    val resultStatuses: LiveData<UIState<List<DataItem>>> get() = _resultStatuses
    var statuses: List<DataItem>? = null
    private var _resultProbabilities = MutableLiveData<UIState<List<DataItem>>>()
    val resultProbabilities: LiveData<UIState<List<DataItem>>> get() = _resultProbabilities
    var probabilities: List<DataItem>? = null

    var form: Form? = savedStateHandle[FORM_KEY]
    var lead: LeadResponse? = savedStateHandle[FormLeadFragment.UPDATE_KEY]

    init {
        getTypes()
        getChannels()
        getMedias()
        getSources()
        getStatuses()
        getProbabilities()
    }

    private fun getTypes() {
        viewModelScope.launch {
            leadsRepository.getTypes().collect {
                _resultTypes.value = it
            }
        }
    }

    private fun getChannels() {
        viewModelScope.launch {
            leadsRepository.getChannels().collect {
                _resultChannels.value = it
            }
        }
    }

    private fun getMedias() {
        viewModelScope.launch {
            leadsRepository.getMedias().collect {
                _resultMedias.value = it
            }
        }
    }

    private fun getSources() {
        viewModelScope.launch {
            leadsRepository.getSources().collect {
                _resultSources.value = it
            }
        }
    }

    private fun getStatuses() {
        viewModelScope.launch {
            leadsRepository.getStatuses().collect {
                _resultStatuses.value = it
            }
        }
    }

    private fun getProbabilities() {
        viewModelScope.launch {
            leadsRepository.getProbabilities().collect {
                _resultProbabilities.value = it
            }
        }
    }

    private var _resultPostLead = MutableLiveData<UIState<LeadResponse>>()
    val resultPostLead: LiveData<UIState<LeadResponse>> get() = _resultPostLead
    fun postLead(postLeadRequest: PostLeadRequest) {
        viewModelScope.launch {
            leadsRepository.postLead(postLeadRequest).collect {
                _resultPostLead.value = it
            }
        }
    }

    fun postUpdateLead(id: String, postLeadRequest: PostLeadRequest) {
        viewModelScope.launch {
            leadsRepository.postUpdateLead(id, postLeadRequest).collect {
                _resultPostLead.value = it
            }
        }
    }

    private var _resultUpdateStatus = MutableLiveData<UIState<DataStatus>>()
    val resultUpdateStatus: LiveData<UIState<DataStatus>> get() = _resultUpdateStatus
    fun patchUpdateStatus(id: Int, updateStatusRequest: UpdateStatusRequest) {
        viewModelScope.launch {
            leadsRepository.patchStatus(id, updateStatusRequest).collect {
                _resultUpdateStatus.value = it
            }
        }
    }
}
