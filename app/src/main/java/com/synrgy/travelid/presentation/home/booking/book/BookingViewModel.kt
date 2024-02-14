package com.synrgy.travelid.presentation.home.booking.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.BookFlightAway
import com.synrgy.travelid.domain.model.main.BookFlightAwayRequest
import com.synrgy.travelid.domain.model.main.ListFlight
import com.synrgy.travelid.domain.model.main.SeatByFlightId
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _bookFlightAway = MutableLiveData<BookFlightAway>()
    val bookFlightAway: LiveData<BookFlightAway> = _bookFlightAway

    private val _showSeatBooked = MutableLiveData<List<SeatByFlightId>>()
    val showSeatBooked: LiveData<List<SeatByFlightId>> = _showSeatBooked

    private val _showUser = MutableLiveData<UserProfile>()
    val showUser: LiveData<UserProfile> = _showUser

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun bookFlightAway(request: BookFlightAwayRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.bookFlightAway(tokenRepository.getToken()!!, request)
                withContext(Dispatchers.Main) {
                    _bookFlightAway.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }

    fun seatByFlightId(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.seatByFlightId(id)
                withContext(Dispatchers.Main) {
                    _showSeatBooked.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }

    fun userProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _showUser.value = mainRepository.userProfile(tokenRepository.getToken()!!)
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}