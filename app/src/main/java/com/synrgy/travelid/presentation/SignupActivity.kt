package com.synrgy.travelid.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.travelid.databinding.ActivitySignupBinding
import com.synrgy.travelid.domain.model.UserRegisterRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
    }

    private fun bindView() {
        binding.btnRegister.setOnClickListener { handleRegisterValidation() }
    }

    private fun handleRegisterValidation() {
        val email = binding.etEmailInput.text.toString()
        val password = binding.etInputPassword.text.toString()
        val fullName = binding.etNameInput.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val dateOfBirth = binding.etDateofbirth.text.toString()

        val request = UserRegisterRequest(email, password, fullName, phoneNumber, dateOfBirth)

        viewModel.userRegister(request)
    }
}