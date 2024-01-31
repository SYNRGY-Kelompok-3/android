package com.synrgy.travelid.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.Notification
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _showUser = MutableLiveData<UserProfile>()
    val showUser: LiveData<UserProfile> = _showUser

    private val _showNotification = MutableLiveData<List<Notification>>()
    val showNotification: LiveData<List<Notification>> = _showNotification

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

    fun notification(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.notification(tokenRepository.getToken()!!, id)
                withContext(Dispatchers.Main) {
                    _showNotification.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}