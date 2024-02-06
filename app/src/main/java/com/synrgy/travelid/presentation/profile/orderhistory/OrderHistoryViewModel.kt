package com.synrgy.travelid.presentation.profile.orderhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _showEmpty = MutableLiveData<Boolean>()
    val showEmpty: LiveData<Boolean> = _showEmpty

    private val _showUser = MutableLiveData<UserProfile>()
    val showUser: LiveData<UserProfile> = _showUser

    private val _showOrderHistory = MutableLiveData<List<OrderHistory>>()
    val showOrderHistory: LiveData<List<OrderHistory>> = _showOrderHistory

    private val _showOrderHistoryById = MutableLiveData<OrderHistoryById>()
    val showOrderHistoryById: LiveData<OrderHistoryById> = _showOrderHistoryById

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun userProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _showUser.value = mainRepository.userProfile(tokenRepository.getToken()!!)
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }

    fun orderHistory(customerId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.orderHistory(tokenRepository.getToken()!!, customerId)
                withContext(Dispatchers.Main) {
                    _showEmpty.value = false
                    _showOrderHistory.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _showEmpty.value = true
                    _error.value = e.message
                }
            }
        }
    }

    fun orderHistoryById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.orderHistoryById(tokenRepository.getToken()!!, id)
                withContext(Dispatchers.Main) {
                    _showOrderHistoryById.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}