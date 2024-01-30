package com.synrgy.travelid.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.synrgy.travelid.domain.model.auth.ErrorMessage
import com.synrgy.travelid.domain.model.auth.UserLogin
import com.synrgy.travelid.domain.model.auth.UserLoginRequest
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
    private val _userLogin = MutableLiveData<UserLogin>()
    val userLogin: LiveData<UserLogin> = _userLogin

    private val _errorLogin = MutableLiveData<ErrorMessage>()
    val errorLogin: LiveData<ErrorMessage> = _errorLogin

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun userLogin(request: UserLoginRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.userLogin(request)
                val token = response.accessToken
                insertToken(token = token)
                withContext(Dispatchers.Main) {
                    _userLogin.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    if(e is retrofit2.HttpException){
                        val error = Gson().fromJson(
                                e.response()?.errorBody()?.string(),
                                ErrorMessage::class.java
                        )
                        _errorLogin.value = ErrorMessage(message = error.message)
                    }
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