package com.synrgy.travelid.presentation.auth.register

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.travelid.databinding.ActivityOtpBinding
import com.synrgy.travelid.domain.model.SendOTPRequest
import com.synrgy.travelid.domain.model.UserConfirmOtpRegister
import com.synrgy.travelid.presentation.auth.forgotpassword.LupaPasswordActivity
import com.synrgy.travelid.presentation.auth.register.RegisterActivity.Companion.USER_EMAIL_REGISTER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 0
    private var otp1: String = ""
    private var otp2: String = ""
    private var otp3: String = ""
    private var otp4: String = ""
    private var otp5: String = ""
    private var otp6: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingView()
        observeViewModel()
    }

    private fun bindingView() {
        binding.btnVerifikasi.setOnClickListener { handleRegisterValidation() }
        binding.tvKirimUlang.setOnClickListener { handleSendVerifCode() }
        binding.etCode1.requestFocus()
        setDataUser()
        setupOtpTextWatchers()
    }

    private fun observeViewModel(){
        viewModel.confirmOTP.observe(this, ::handleResponse)
    }

    private fun handleRegisterValidation() {
        otp1 = binding.etCode1.text.toString()
        otp2 = binding.etCode2.text.toString()
        otp3 = binding.etCode3.text.toString()
        otp4 = binding.etCode4.text.toString()
        otp5 = binding.etCode5.text.toString()
        otp6 = binding.etCode6.text.toString()

        val otpCode = "${otp1}${otp2}${otp3}${otp4}${otp5}${otp6}"

        viewModel.userConfirmOtpRegister(otpCode)
    }

    private fun handleSendVerifCode() {
        val userEmail = intent.getStringExtra(LupaPasswordActivity.USER_EMAIL)
        val request = SendOTPRequest(userEmail.toString())

        viewModel.sendOTPRegister(request)
        startCountdown()
    }

    private fun setDataUser() {
        val userEmail = intent.getStringExtra(USER_EMAIL_REGISTER)
        binding.tvEmail.text = userEmail
    }

    private fun handleResponse(userConfirmOtpRegister: UserConfirmOtpRegister) {
        if(userConfirmOtpRegister.message == "sukses"){ handleOpenBottomSheetLogin() }
    }

    private fun handleOpenBottomSheetLogin() {
        val bottomSheetFragment = SuccessRegisterFragment()
        bottomSheetFragment.isCancelable = false
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupOtpTextWatchers() {
        val editTextIds = listOf(
            binding.etCode1, binding.etCode2, binding.etCode3,
            binding.etCode4, binding.etCode5, binding.etCode6
        )

        for (i in 0 until editTextIds.size - 1) {
            val currentEditText = editTextIds[i]
            val nextEditText = editTextIds[i + 1]

            currentEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        nextEditText.requestFocus()
                    }
                }
            })
        }

        val lastEditText = editTextIds.last()
        lastEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    binding.etCode1.clearFocus()
                }
            }
        })
    }

    private fun startCountdown() {
        val countdownTimeInMillis: Long = 1 * 60 * 1000

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(countdownTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                timeRemaining = 0
                updateCountdownText()
                binding.tvKirimUlang.isEnabled = true
                binding.tvKirimUlang.text = "Kirim ulang"
            }
        }.start()

        binding.tvKirimUlang.isEnabled = false
    }

    private fun updateCountdownText() {
        val minutes = (timeRemaining / 1000) / 60
        val seconds = (timeRemaining / 1000) % 60
        val countdownText = String.format("%02d:%02d", minutes, seconds)
        binding.tvKirimUlang.text = countdownText
    }
}