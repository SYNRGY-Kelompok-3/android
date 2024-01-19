package com.synrgy.travelid.presentation.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.ResetPassword
import com.synrgy.travelid.domain.model.UpdatePasswordRequest
import com.synrgy.travelid.domain.model.ValidateOTP
import com.synrgy.travelid.domain.model.ValidateOTPRequest
import com.synrgy.travelid.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LupaPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _resetPasswordResponse = MutableLiveData<ResetPassword>()
    val resetPasswordResponse: LiveData<ResetPassword> = _resetPasswordResponse

    private val _validateOTP = MutableLiveData<ValidateOTP>()
    val validateOTP: LiveData<ValidateOTP> = _validateOTP

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun resetPassword(request: ResetPasswordRequest){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = authRepository.lupaPassword(request)
                withContext(Dispatchers.Main){
                    _resetPasswordResponse.value = response
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }

    fun validateOTP(request: ValidateOTPRequest){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = authRepository.validateOTP(request)
                withContext(Dispatchers.Main){
                    _validateOTP.value = response
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }

    fun updatePassword(request: UpdatePasswordRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    authRepository.aturPassword(request)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}