package com.synrgy.travelid.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.travelid.databinding.ActivityConfirmOtpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmOtpBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
    }

    private fun bindView() {
        binding.btnConfirmOtp.setOnClickListener { handleConfirmOtpRegistration() }
    }

    private fun handleConfirmOtpRegistration() {
        val otp1 = binding.etOtp1.text.toString()
        val otp2 = binding.etOtp2.text.toString()
        val otp3 = binding.etOtp3.text.toString()
        val otp4 = binding.etOtp4.text.toString()
        val otp5 = binding.etOtp5.text.toString()
        val otp6 = binding.etOtp6.text.toString()

        val otp = "$otp1$otp2$otp3$otp4$otp5$otp6"

        viewModel.userConfirmOtpRegister(otp)
    }
}