package com.synrgy.travelid.presentation.home.booking.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.BookFlightAway
import com.synrgy.travelid.domain.model.main.BookFlightAwayRequest
import com.synrgy.travelid.domain.model.main.PaymentBook
import com.synrgy.travelid.domain.model.main.PaymentBookRequest
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _paymentBook = MutableLiveData<PaymentBook>()
    val paymentBook: LiveData<PaymentBook> = _paymentBook

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun paymentBook(request: PaymentBookRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.paymentBook(tokenRepository.getToken()!!, request)
                withContext(Dispatchers.Main) {
                    _paymentBook.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}