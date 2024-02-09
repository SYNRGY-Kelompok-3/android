package com.synrgy.travelid.presentation.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.common.reduceFileImage
import com.synrgy.travelid.domain.model.main.EditProfile
import com.synrgy.travelid.domain.model.main.EditProfilePicture
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.domain.repository.AuthRepository
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _showUser = MutableLiveData<UserProfile>()
    val showUser: LiveData<UserProfile> = _showUser

    private val _editProfile = MutableLiveData<EditProfile>()
    val editProfile : LiveData<EditProfile> = _editProfile

    private val _editProfilePicture = MutableLiveData<EditProfilePicture>()
    val editProfilePicture : LiveData<EditProfilePicture> = _editProfilePicture

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

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

    fun editProfile(request: EditProfileRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.editProfile(
                    tokenRepository.getToken()!!, request
                )
                withContext(Dispatchers.Main) {
                    _editProfile.value = response
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }

    fun editProfilePicture(
        profilePicture: File?,
        idCustomer: Int
    ) {
        val requestFile = profilePicture?.let { reduceFileImage(it).asRequestBody("image/png".toMediaTypeOrNull()) }
        val picture = requestFile?.let { MultipartBody.Part.createFormData("profilePicture", profilePicture.name, it) }
        val id = idCustomer.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = mainRepository.editProfilePicture(
                    token = tokenRepository.getToken()!!,
                    profilePicture = picture,
                    idCustomer = id
                )
                withContext(Dispatchers.Main){
                    _editProfilePicture.value = response
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
}