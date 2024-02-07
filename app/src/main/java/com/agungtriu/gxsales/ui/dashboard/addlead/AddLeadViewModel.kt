package com.agungtriu.gxsales.ui.dashboard.addlead

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.gxsales.data.LeadsRepository
import com.agungtriu.gxsales.data.remote.response.DataItem
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.ui.dashboard.location.Location
import com.agungtriu.gxsales.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLeadViewModel @Inject constructor(
    private val leadsRepository: LeadsRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private var _resultLead = MutableLiveData<UIState<LeadResponse>>()
    val resultLead: LiveData<UIState<LeadResponse>> get() = _resultLead

    private var _resultBranchOffices = MutableLiveData<UIState<List<DataItem>>>()
    val resultBranchOffices: LiveData<UIState<List<DataItem>>> get() = _resultBranchOffices

    private var _resultLocation = MutableLiveData<Location>()
    val resultLocation: LiveData<Location> get() = _resultLocation
    var dataLocation: Location = Location()
    var lead : LeadResponse?= null

    var id: Int? = savedStateHandle[AddLeadFragment.UPDATE_KEY]

    var branchOffices: List<DataItem>? = null

    init {
        getBranchOffices()
        id?.let { getLead(it) }
    }

    private fun getBranchOffices() {
        viewModelScope.launch {
            leadsRepository.getBranchOffices().collect {
                _resultBranchOffices.value = it
            }
        }
    }

    private fun getLead(id: Int) {
        viewModelScope.launch {
            leadsRepository.getLead(id).collect {
                _resultLead.value = it
            }
        }
    }

    fun setLocation(location: Location) {
        _resultLocation.value = location
    }

}