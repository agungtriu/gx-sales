package com.agungtriu.gxsales.ui.dashboard.leads.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.LeadsRepository
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val leadsRepository: LeadsRepository) :
    ViewModel() {

    private var _resultStatuses = MutableLiveData<UIState<List<DataItem>>>()
    val resultStatuses: LiveData<UIState<List<DataItem>>> get() = _resultStatuses
    private var _resultChannels = MutableLiveData<UIState<List<DataItem>>>()
    val resultChannels: LiveData<UIState<List<DataItem>>> get() = _resultChannels
    private var _resultMedias = MutableLiveData<UIState<List<DataItem>>>()
    val resultMedias: LiveData<UIState<List<DataItem>>> get() = _resultMedias
    private var _resultSources = MutableLiveData<UIState<List<DataItem>>>()
    val resultSources: LiveData<UIState<List<DataItem>>> get() = _resultSources

    var filter = Filter()

    init {
        getStatuses()
        getChannels()
        getMedias()
        getSources()
    }

    private fun getStatuses() {
        viewModelScope.launch {
            leadsRepository.getStatuses().collect {
                _resultStatuses.value = it
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
}
