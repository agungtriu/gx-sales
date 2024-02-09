package com.agungtriu.gxsales.ui.dashboard.leads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.LeadsRepository
import com.agungtriu.gxsales.data.remote.response.DeleteLeadResponse
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(private val leadsRepository: LeadsRepository) :
    ViewModel() {
    private var _resultLeads = MutableLiveData<UIState<List<LeadResponse>>>()
    val resultLeads: LiveData<UIState<List<LeadResponse>>> get() = _resultLeads
    var filterSearch = FilterSearch()

    init {
        getLeads()
    }

    fun getLeads() {
        viewModelScope.launch {
            leadsRepository.getLeads().collect {
                _resultLeads.value = it
            }
        }
    }

    private var _resultDelete = MutableLiveData<UIState<DeleteLeadResponse>>()
    val resultDelete: LiveData<UIState<DeleteLeadResponse>> get() = _resultDelete
    fun delete(id: Int) {
        viewModelScope.launch {
            leadsRepository.delete(id).collect {
                _resultDelete.value = it
            }
        }
    }
}
