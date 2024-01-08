package com.synrgy.travelid

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.repo.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LupaPasswordViewModel (
//    private val lupaPasswordUseCase: LupaPasswordUseCase,
    private val authRepository: AuthRepository

): ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun resetPassword(request: ResetPasswordRequest){
        viewModelScope.launch(Dispatchers.IO){
            try{
                withContext(Dispatchers.Main){
                    authRepository.lupaPassword(request)
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
}