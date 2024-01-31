package com.synrgy.travelid.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.synrgy.travelid.domain.model.auth.ErrorMessage
import com.synrgy.travelid.domain.model.auth.SendOTP
import com.synrgy.travelid.domain.model.auth.SendOTPRequest
import com.synrgy.travelid.domain.model.auth.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.auth.UserRegister
import com.synrgy.travelid.domain.model.auth.UserRegisterRequest
import com.synrgy.travelid.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userRegister = MutableLiveData<UserRegister>()
    val userRegister: LiveData<UserRegister> = _userRegister

    private val _confirmOTP = MutableLiveData<UserConfirmOtpRegister>()
    val confirmOTP: LiveData<UserConfirmOtpRegister> = _confirmOTP

    private val _sendOTP = MutableLiveData<SendOTP>()
    val sendOTP: LiveData<SendOTP> = _sendOTP

    private val _errorRegister = MutableLiveData<ErrorMessage>()
    val errorRegister: LiveData<ErrorMessage> = _errorRegister

    private val _errorOTP = MutableLiveData<ErrorMessage>()
    val errorOTP: LiveData<ErrorMessage> = _errorOTP

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun userRegister(request: UserRegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.userRegister(request)
                withContext(Dispatchers.Main) {
                    _userRegister.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    if(e is retrofit2.HttpException){
                        val error =
                            Gson().fromJson(
                                e.response()?.errorBody()?.string(),
                                ErrorMessage::class.java
                            )
                        _errorRegister.value = ErrorMessage(message = error.message)
                    }
                }
            }
        }
    }

    fun userConfirmOtpRegister(otp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.userConfirmOtpRegister(otp)
                withContext(Dispatchers.Main) {
                    _confirmOTP.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "OTP yang kamu masukan salah"
                }
            }
        }
    }

    fun sendOTPRegister(request: SendOTPRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.sendOTPRegister(request)
                withContext(Dispatchers.Main) {
                    _sendOTP.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}