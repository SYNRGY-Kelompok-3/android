package com.febi.projek

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.febi.projek.domain.model.auth.ResetPasswordRequest
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LupaPasswordViewModel (
    private val lupaPasswordUseCase: LupaPasswordUseCase,

): ViewModel() {
    private val _openBottomSheet = MutableLiveData<Boolean>()
    val openBottomSheet: LiveData<Boolean> = _openBottomSheet

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun resetPassword(request: ResetPasswordRequest){
        viewModelScope.launch(Dispatchers.IO){
            try{
                withContext(Dispatchers.Main){
                    _openBottomSheet.value = lupaPasswordUseCase.invoke(request)
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
}