package com.synrgy.travelid.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.UserLoginRequest
import com.synrgy.travelid.domain.repository.AuthRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _openHomePage = MutableLiveData<Boolean>()
    val openHomePage: LiveData<Boolean> = _openHomePage

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun userLogin(request: UserLoginRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.userLogin(request)
                val token = response.accessToken
                insertToken(token = token)
                withContext(Dispatchers.Main) {
                    _openHomePage.value = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }

    private fun insertToken(token: String){
        if(token.isNotEmpty()){
            viewModelScope.launch {
                tokenRepository.setToken(token)
            }
        }
    }
}