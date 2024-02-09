package com.synrgy.travelid.presentation.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.FlightById
import com.synrgy.travelid.domain.model.main.ListFlight
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private val _showListFlight = MutableLiveData<List<ListFlight>>()
    val showListFlight: LiveData<List<ListFlight>> = _showListFlight

    private val _showFlightyById = MutableLiveData<FlightById>()
    val showFlightyById: LiveData<FlightById> = _showFlightyById

    private val _showEmpty = MutableLiveData<Boolean>()
    val showEmpty: LiveData<Boolean> = _showEmpty

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun listFlight(
        page: Int,
        size: Int,
        originCity: String,
        destinationCity: String,
        startDate: String,
        endDate: String,
        pasangerClass: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.searchFlight(
                    page,
                    size,
                    originCity,
                    destinationCity,
                    startDate,
                    endDate,
                    pasangerClass)
                withContext(Dispatchers.Main) {
                    _showListFlight.value = response
                    _showEmpty.value = response.isEmpty()
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _showEmpty.value = false
                    _error.value = e.message
                }
            }
        }
    }

    fun flightById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.flightById(id)
                withContext(Dispatchers.Main) {
                    _showFlightyById.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}