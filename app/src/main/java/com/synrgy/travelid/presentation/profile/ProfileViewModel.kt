package com.synrgy.travelid.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel(){
    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    private val _notLoggedIn = MutableLiveData<Boolean>()
    val notLoggedIn: LiveData<Boolean> = _notLoggedIn

    private val _showUser = MutableLiveData<UserProfile>()
    val showUser: LiveData<UserProfile> = _showUser

    private val _openLoginPage = MutableLiveData<Boolean>()
    val openLoginPage: LiveData<Boolean> = _openLoginPage

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun checkLoggedIn(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val isLoggedIn = tokenRepository.getToken().isNullOrEmpty()
                withContext(Dispatchers.Main){
                    if(isLoggedIn) _notLoggedIn.value = true
                    else _loggedIn.value = true
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }

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

    fun clearDataUser(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                tokenRepository.clearToken()
                withContext(Dispatchers.Main) {
                    _openLoginPage.value = true
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
}