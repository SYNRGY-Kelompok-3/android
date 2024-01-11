package com.synrgy.travelid.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.UserRegisterRequest
import com.synrgy.travelid.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun userRegister(request: UserRegisterRequest){
        viewModelScope.launch(Dispatchers.IO){
            try{
                withContext(Dispatchers.Main){
                    authRepository.userRegister(request)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }

    fun userConfirmOtpRegister(otp: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                withContext(Dispatchers.Main){
                    authRepository.userConfirmOtpRegister(otp)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
}