package com.synrgy.travelid.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.ResetPassword
import com.synrgy.travelid.domain.model.SendOTP
import com.synrgy.travelid.domain.model.SendOTPRequest
import com.synrgy.travelid.domain.model.UserConfirmOtpRegister
import com.synrgy.travelid.domain.model.UserRegister
import com.synrgy.travelid.domain.model.UserRegisterRequest
import com.synrgy.travelid.domain.model.ValidateOTP
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
                    _error.value = e.message
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
                    _error.value = e.message
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