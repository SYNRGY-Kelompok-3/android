package com.synrgy.travelid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityOtpBinding
import com.synrgy.travelid.domain.model.UserRegisterRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingView()
    }

    private fun bindingView() {
        binding.btnSendOtp.setOnClickListener { handleRegisterValidation() }
    }

    private fun handleRegisterValidation() {
        val otp1 = binding.etCode1.text.toString()
        val otp2 = binding.etCode2.text.toString()
        val otp3 = binding.etCode3.text.toString()
        val otp4 = binding.etCode4.text.toString()
        val otp5 = binding.etCode5.text.toString()
        val otp6 = binding.etCode6.text.toString()

        val otpCode = "${otp1}${otp2}${otp3}${otp4}${otp5}${otp6}"

        viewModel.userConfirmOtpRegister(otpCode)
    }
}