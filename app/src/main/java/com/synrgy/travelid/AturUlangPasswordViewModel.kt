package com.synrgy.travelid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import com.synrgy.travelid.domain.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AturUlangPasswordViewModel (
    private val authRepository: AuthRepository
): ViewModel() {
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

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