package com.synrgy.travelid.presentation.home.booking.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.InvoiceBooking
import com.synrgy.travelid.domain.model.main.OrderHistoryById
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _showInvoice = MutableLiveData<InvoiceBooking>()
    val showInvoice: LiveData<InvoiceBooking> = _showInvoice

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun invoiceBooking(bookId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.invoiceBooking(tokenRepository.getToken()!!, bookId)
                withContext(Dispatchers.Main) {
                    _showInvoice.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}